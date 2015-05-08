package com.heliumv.api.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.sql.Timestamp;


import java.text.MessageFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.api.item.IItemApiV11;
import com.heliumv.api.item.ItemApiV11;
import com.heliumv.api.item.ItemEntry;
import com.heliumv.api.item.ItemEntryList;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IJCRDocCall;
import com.heliumv.factory.IPartnerCall;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.partner.service.PartnerDto;
import com.lp.server.system.jcr.service.JCRDocDto;
import com.lp.server.system.jcr.service.JCRDocFac;
import com.lp.server.system.jcr.service.docnode.DocNodeArtikel;
import com.lp.server.system.jcr.service.docnode.DocNodeFile;
import com.lp.server.system.jcr.service.docnode.DocPath;

@Service("hvCruisecontrol")
@Path("/api/internal/cruisecontrol/")
public class CruisecontrolApi extends BaseApi implements ICruisecontrolApi {
	private AbstractProjectWorker buildnrWorker ;
	private AbstractProjectWorker deploynrWorker ;
	
	@Autowired
	private IGlobalInfo globalInfo ;	
	@Autowired
	private IJCRDocCall jcrDocCall ;
	@Autowired
	private IItemApiV11 hvItemV11 ;
	@Autowired
	private IArtikelCall artikelCall ;
	@Autowired
	private IPartnerCall partnerCall ;
	
	public static class ApiParam {
		public final static String MESSAGE = "message" ;
		public final static String BUILDLABEL = "buildlabel" ;
	}
		
	public void setBuildnrWorker(AbstractProjectWorker worker) {
		buildnrWorker = worker ;
	}
	
	public void setDeploynrWorker(AbstractProjectWorker worker) {
		deploynrWorker = worker ;
	}
	
	@Override
	@GET
	@POST
	@Path("/buildmessage")
	public void setBuildNumber(
			@QueryParam(Param.USERID) String userid,
			@QueryParam(ApiParam.MESSAGE) String message, 
			@QueryParam(ApiParam.BUILDLABEL) String buildLabel) {
		buildnrWorker.setApi(this);
		buildnrWorker.workOnMessage(userid, message, buildLabel);
	}
	
	@Override
	@GET
	@POST
	@Path("/deploymessage")
	public void setDeployNumber(
			@QueryParam(Param.USERID) String userid,
			@QueryParam(ApiParam.MESSAGE) String message, 
			@QueryParam(ApiParam.BUILDLABEL) String buildLabel) {
		deploynrWorker.setApi(this);
		deploynrWorker.workOnMessage(userid, message, buildLabel);
	}

	@Override
	@GET
	@POST
	@Path("/buildjenkins")
	public void setBuildNumberJenkins(
			@QueryParam(Param.USERID) String userid,
			@QueryParam(ApiParam.BUILDLABEL) String buildLabel) throws NamingException {
		buildnrWorker.setApi(this);
		
		try {
			String changeLog = retrieveJenkinsChangelog(buildLabel) ;
			if(changeLog != null) {
				JenkinsChangesParser parser = new JenkinsChangesParser(changeLog) ;
				while(parser.parse()) {
					buildnrWorker.workOnMessage(userid, parser.getCommitMessage(), buildLabel);						
				}			
			}
		} catch(IOException e) {
			respondExpectationFailed(20001) ;
		}
	}

	@Override
	@GET
	@POST
	@Path("/deployjenkins")
	public void setDeployNumberJenkins(
			@QueryParam(Param.USERID) String userid,
			@QueryParam(ApiParam.BUILDLABEL) String buildLabel) throws NamingException {
		deploynrWorker.setApi(this);
		
		try {
			String changeLog = retrieveJenkinsChangelog(buildLabel) ;
			if(changeLog != null) {
				JenkinsChangesParser parser = new JenkinsChangesParser(changeLog) ;
				while(parser.parse()) {
					deploynrWorker.workOnMessage(userid, parser.getCommitMessage(), buildLabel);			
				}			
			}
		} catch(IOException e) {
			respondExpectationFailed(20001) ;
		}
	}
	
	
	private String retrieveJenkinsChangelog(String buildNumber) throws ClientProtocolException, NamingException, IOException {
		Context env = (Context) new InitialContext().lookup("java:comp/env") ;
		String jenkinsEndpoint = (String) env.lookup("jenkins.changelog.endpoint") ;
		String uri = MessageFormat.format(jenkinsEndpoint, buildNumber) ;
//		String uri = "http://jenkins:8081/job/Helium-Deploy%20mit%20Ant/" 
//				+ buildNumber + "/api/xml?wrapper=changes&xpath=//changeSet//item/msg" ;
		
		HttpGet httpGet = new HttpGet(uri) ;
		httpGet.addHeader("accept", "application/xml;charset=UTF-8");

		HttpClient client = new DefaultHttpClient() ;
		HttpResponse response = client.execute(httpGet) ;
		StatusLine status = response.getStatusLine() ;
		if(status.getStatusCode() != 200) {
			client.getConnectionManager().shutdown(); 
			return null ;
		}
		
		HttpEntity anEntity = response.getEntity() ;
		InputStream s = anEntity.getContent() ;
		BufferedReader br = new BufferedReader(new InputStreamReader(s)) ;
		String theContent = "" ;
		String line = ""; 
		while((line = br.readLine()) != null) {
			theContent += line + "\n" ;
		}		

		client.getConnectionManager().shutdown(); 
		return theContent ;
	}
	
	@Override
	@GET
	@POST
	@Path("/jcrtest")
	public void callJCR(
			@QueryParam(Param.USERID) String userid,
			@QueryParam("count") Integer count
	) throws RemoteException, NamingException, Exception {
		if(connectClient(userid) == null) return ;
		
		if(count == null) {
			count = new Integer(1) ;
		}
		
		ItemEntryList items = ((ItemApiV11)hvItemV11).getItemsListImpl(10, null, null, null, null, null, null, null, null, null, null, null) ;
		if(items.getEntries().size() == 0) {
			respondNotFound();
			return ;
		}
		
		ItemEntry entry = items.getEntries().get(1) ;
		ArtikelDto artikelDto = artikelCall.artikelFindByPrimaryKeySmallOhneExc(entry.getId()) ;
		if(artikelDto == null) {
			respondNotFound(); 
			return ;
		}
		
		String xmlContent = "<xml><tag>HansDampfSample</tag></xml>" ;
		archiveXmlDocument(count, artikelDto, xmlContent);
	}
	
	private void archiveXmlDocument(Integer count, ArtikelDto artikelDto, String xmlContent) throws RemoteException, NamingException {	
		Integer partnerId = globalInfo.getTheClientDto().getIDPersonal() ;
		PartnerDto partnerDto = partnerCall.partnerFindByPrimaryKey(partnerId);
		if(partnerDto == null) {
			respondBadRequest("partnerId", partnerId.toString());
			return ;
		}
		
		for(int i = 1; i <= count; i++) {
			Timestamp ts = new Timestamp(System.currentTimeMillis()) ;
			String s = "" + i ;
			while(s.length() < 7) {
				s = "0" + s ;
			}
			String filename = "Import_Test-" + ts.toString() + "-" + s + ".xml" ; 
			
			JCRDocDto jcrDocDto = new JCRDocDto();
			DocPath dp = new DocPath(new DocNodeArtikel(artikelDto)).add(new DocNodeFile(filename)) ;
			jcrDocDto.setDocPath(dp) ;
			jcrDocDto.setbData(xmlContent.getBytes());
			jcrDocDto.setbVersteckt(false);
			jcrDocDto.setlAnleger(partnerDto.getIId());
			
			jcrDocDto.setlPartner(partnerDto.getIId());
			jcrDocDto.setlSicherheitsstufe(JCRDocFac.SECURITY_ARCHIV);
//			jcrDocDto.setlVersion(0);
			jcrDocDto.setlZeitpunkt(ts.getTime());
			jcrDocDto.setsBelegart(JCRDocFac.DEFAULT_ARCHIV_BELEGART);
			jcrDocDto.setsGruppierung(JCRDocFac.DEFAULT_ARCHIV_GRUPPE);
			jcrDocDto.setsBelegnummer(artikelDto.getCNr().replace("/", "."));
			jcrDocDto.setsFilename(artikelDto.getCNr().replace("/", "."));
//			jcrDocDto.setDocPath(new DocPath(sPath + "/Import Clevercure "
//					+ auftragDto.getIId()));
			jcrDocDto.setsMIME(".xml");
			jcrDocDto.setsName("Import Clevercure" + artikelDto.getIId());
			jcrDocDto.setsRow(artikelDto.getIId().toString());
			jcrDocDto.setsTable("ARTIKEL");
			String sSchlagworte = "Import Test Orderresponse";
			jcrDocDto.setsSchlagworte(sSchlagworte);
			jcrDocCall.addNewDocumentOrNewVersionOfDocument(jcrDocDto);		
			
			jcrDocCall.checkIfNodeExists(dp) ;			

			DocPath dpBad = new DocPath(new DocNodeArtikel(artikelDto)).add(new DocNodeFile("bad_" + filename)) ;
			jcrDocCall.checkIfNodeExists(dpBad) ;
		}
	}	
}

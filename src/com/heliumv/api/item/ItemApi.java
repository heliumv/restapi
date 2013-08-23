package com.heliumv.api.item;

import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.util.EJBExceptionLP;

@Service("hvItem")
@Path("/api/v1/item")
public class ItemApi extends BaseApi implements IItemApi {
	@Autowired
	private IArtikelCall artikelCall ;

	
	@Override
	@GET
	@Path("/{userid}")
	@Produces({"application/json", "application/xml"})
	public ItemEntry findItemByCnr(
			@PathParam("userid") String userId,
			@QueryParam("cnr") String cnr) {

		if(StringHelper.isEmpty(cnr)) {
			respondBadRequest("cnr", cnr) ;
			return null ;
		}

		if(connectClient(userId) == null) return null ;

		try {
			ItemEntry itemEntry = findItemEntryByCnrImpl(cnr) ;
			if(itemEntry == null) {
				respondNotFound() ;				
			}
			return itemEntry ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
		
		return null;
	}
	
	private ItemEntry findItemEntryByCnrImpl(String cnr) throws RemoteException, NamingException {
		ArtikelDto artikelDto = artikelCall.artikelFindByCNrOhneExc(cnr) ;
		if(artikelDto == null) return null ;

		ItemEntryMapper mapper = new ItemEntryMapper() ;
		return mapper.mapEntry(artikelDto) ;
	}
}

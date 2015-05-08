package com.heliumv.api.stock;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.api.item.StockEntry;
import com.heliumv.api.item.StockEntryMapper;
import com.heliumv.factory.ILagerCall;
import com.heliumv.factory.legacy.AllLagerEntry;
import com.lp.server.artikel.service.LagerDto;
import com.lp.util.EJBExceptionLP;

@Service("hvStock")
@Path("/api/v1/stock")
public class StockApi extends BaseApi implements IStockApi {

	@Autowired
	ILagerCall lagerCall ;
	
	@Override
	@GET
	@Produces({FORMAT_JSON, FORMAT_XML})	
	public StockEntryList getStockList(
			@QueryParam(Param.USERID) String userId) {
		StockEntryList stockEntries = new StockEntryList() ;
		if(connectClient(userId) == null) return stockEntries ;

		try {
			List<StockEntry> entries = new ArrayList<StockEntry>() ;
			StockEntryMapper stockMapper = new StockEntryMapper() ;
			List<AllLagerEntry> stocks = lagerCall.getAllLager() ;
			for (AllLagerEntry allLagerEntry : stocks) {
				if(lagerCall.hatRolleBerechtigungAufLager(allLagerEntry.getStockId())) {
					LagerDto lagerDto = lagerCall.lagerFindByPrimaryKeyOhneExc(allLagerEntry.getStockId()) ;
					if(lagerDto.getBVersteckt() > 0) continue ;
					
					entries.add(stockMapper.mapEntry(lagerDto)) ;
				}
			}
			
			stockEntries.setEntries(entries) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e);
		}
		
		return stockEntries ;
	}

}

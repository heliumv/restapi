package com.heliumv.api.order;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.query.AuftragQuery;
import com.heliumv.factory.query.AuftragpositionQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

@Service("hvOrder")
@Path("/api/v1/order/")
public class OrderApi extends BaseApi implements IOrderApi  {

	@Autowired
	private IParameterCall parameterCall ;
	
	@Autowired
	private IMandantCall mandantCall ;
	
	@Autowired
	private AuftragQuery orderQuery ;
	
	@Autowired
	private AuftragpositionQuery orderPositionQuery ;
	
	@GET
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<OrderEntry> getOrders(
			@QueryParam("userid") String userId,
			@QueryParam("limit") Integer limit,
			@QueryParam("startIndex") Integer startIndex,
			@QueryParam("filter_cnr") String filterCnr,
			@QueryParam("filter_customer") String filterCustomer, 
			@QueryParam("filter_project") String filterProject,
			@QueryParam("filter_withHidden") Boolean filterWithHidden) {
		List<OrderEntry> orders = new ArrayList<OrderEntry>() ;
	
		try {
			if(null == connectClient(userId)) return orders ;
			if(!mandantCall.hasModulAuftrag()) {
				respondNotFound() ;
				return orders ;
			}
			
			FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
			collector.add(orderQuery.getFilterCnr(StringHelper.removeXssDelimiters(filterCnr))) ;
			collector.add(orderQuery.getFilterProject(StringHelper.removeXssDelimiters(filterProject))) ;
			collector.add(orderQuery.getFilterCustomer(StringHelper.removeXssDelimiters(filterCustomer))) ;
			collector.add(orderQuery.getFilterWithHidden(filterWithHidden)) ;
			FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;
			
			QueryParameters params = orderQuery.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;

			QueryResult result = orderQuery.setQuery(params) ;
			orders = orderQuery.getResultList(result) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
			e.printStackTrace() ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
			e.printStackTrace() ;
		}
		
		return orders ;
	}
	
	
	@GET
	@Path("{orderid}/position")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<OrderpositionEntry> getPositions(
			@PathParam("orderid") Integer orderId,
			@QueryParam("userid") String userId,
			@QueryParam("limit") Integer limit,
			@QueryParam("startIndex") Integer startIndex) {
		List<OrderpositionEntry> positions = new ArrayList<OrderpositionEntry>() ;
		try {
			if(connectClient(userId) == null) return positions ;
			if(!mandantCall.hasModulAuftrag()) {
				respondNotFound() ;
				return positions ;
			}
			
			FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
			collector.add(orderPositionQuery.getOrderIdFilter(orderId)) ;
			
//			collector.add(buildFilterCnr(filterCnr)) ;
//			collector.add(buildFilterProject(filterProject)) ;
//			collector.add(buildFilterCustomer(filterCustomer)) ;
//			collector.add(buildFilterWithHidden(filterWithHidden)) ;
			FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;
			
			QueryParameters params = orderPositionQuery.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;

			QueryResult result = orderPositionQuery.setQuery(params) ;
			positions = orderPositionQuery.getResultList(result) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
//		} catch(RemoteException e) {
//			respondUnavailable(e) ;
		} finally {
		}
		return positions ;
	}
}

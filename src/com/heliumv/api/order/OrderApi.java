package com.heliumv.api.order;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IAuftragCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.query.AuftragQuery;
import com.heliumv.factory.query.AuftragQueryOffline;
import com.heliumv.factory.query.AuftragpositionQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.auftrag.service.AuftragDto;
import com.lp.server.auftrag.service.AuftragHandlerFeature;
import com.lp.server.auftrag.service.AuftragQueryResult;
import com.lp.server.partner.service.IAddressContact;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryParametersFeatures;
import com.lp.server.util.fastlanereader.service.query.QueryResult;
import com.lp.util.EJBExceptionLP;

@Service("hvOrder")
@Path("/api/v1/order/")
public class OrderApi extends BaseApi implements IOrderApi  {

	@Autowired
	private IParameterCall parameterCall ;
	
	@Autowired
	private IMandantCall mandantCall ;
	
	@Autowired
	private IAuftragCall auftragCall ;
	
	@Autowired
	private AuftragQuery orderQuery ;
	
	@Autowired
	private AuftragQueryOffline offlineOrderQuery ;
	
	@Autowired
	private AuftragpositionQuery orderPositionQuery ;
	
	@Autowired
	private IGlobalInfo globalInfo ;
	
	@Autowired
	private ModelMapper modelMapper ;
	
	@GET
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<OrderEntry> getOrders(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex,
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
			@PathParam(Param.ORDERID) Integer orderId,
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex) {
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
	
	@GET
	@Path("position")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<OrderpositionsEntry> getOrderPositions(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex,
			@QueryParam("filter_cnr") String filterCnr,
			@QueryParam("filter_customer") String filterCustomer, 
			@QueryParam("filter_project") String filterProject,
			@QueryParam("filter_withHidden") Boolean filterWithHidden) {	
		List<OrderpositionsEntry> entries = new ArrayList<OrderpositionsEntry>() ;

		try {
			if(null == connectClient(userId)) return entries ;
			if(!mandantCall.hasModulAuftrag()) {
				respondNotFound() ;
				return entries ;
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
			List<OrderEntry> orders = orderQuery.getResultList(result) ;
			
			for (OrderEntry orderEntry : orders) {
				collector = new FilterKriteriumCollector() ;
				collector.add(orderPositionQuery.getOrderIdFilter(orderEntry.getId())) ;
				
				filterCrits = new FilterBlock(collector.asArray(), "AND")  ;
				
				params = orderPositionQuery.getDefaultQueryParameters(filterCrits) ;
				params.setLimit(Integer.MAX_VALUE) ;
				params.setKeyOfSelectedRow(0) ;

				QueryResult positionResult = orderPositionQuery.setQuery(params) ;
				List<OrderpositionEntry> posEntries = orderPositionQuery.getResultList(positionResult) ;	
				
				addPositionEntries(entries, orderEntry.getId(), posEntries);
			}
		} catch(NamingException e) {
			respondUnavailable(e) ;
			e.printStackTrace() ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
			e.printStackTrace() ;
		}
		
		return entries ;
	}
	
	private void addPositionEntries(List<OrderpositionsEntry> allEntries, Integer orderId, List<OrderpositionEntry> posEntries) {
		for (OrderpositionEntry orderpositionEntry : posEntries) {
			OrderpositionsEntry entry = new OrderpositionsEntry(orderId, orderpositionEntry) ;			
			allEntries.add(entry) ;
		}
	}
	
	@GET
	@Path("offline")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public OfflineOrderEntry getOfflineOrders(
			@HeaderParam(ParamInHeader.TOKEN) String headerUserId,
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex,
			@QueryParam("filter_cnr") String filterCnr,
			@QueryParam("filter_customer") String filterCustomer, 
			@QueryParam("filter_delivery_customer") String filterDeliveryCustomer, 
			@QueryParam("filter_project") String filterProject,
			@QueryParam("filter_withHidden") Boolean filterWithHidden) {
		OfflineOrderEntry entry = new OfflineOrderEntry() ;

		try {
			if(null == connectClient(headerUserId, userId)) return entry ;
			if(!mandantCall.hasModulAuftrag()) {
				respondNotFound() ;
				return entry ;
			}
			
			FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
			collector.add(offlineOrderQuery.getFilterCnr(StringHelper.removeXssDelimiters(filterCnr))) ;
			collector.add(offlineOrderQuery.getFilterProject(StringHelper.removeXssDelimiters(filterProject))) ;
			collector.add(offlineOrderQuery.getFilterCustomer(StringHelper.removeXssDelimiters(filterCustomer))) ;
			collector.add(offlineOrderQuery.getFilterDeliveryCustomer(StringHelper.removeXssDelimiters(filterDeliveryCustomer))) ;
			collector.add(offlineOrderQuery.getFilterWithHidden(filterWithHidden)) ;
			FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;
			
			QueryParametersFeatures params = offlineOrderQuery.getFeatureQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;
			params.addFeature(AuftragHandlerFeature.ADRESSE_KOMPLETT) ;
			params.addFeature(AuftragHandlerFeature.ADRESSE_ANSCHRIFT);
			params.addFeature(AuftragHandlerFeature.ADRESSE_IST_LIEFERADRESSE);
			AuftragQueryResult result = (AuftragQueryResult) offlineOrderQuery.setQuery(params) ;

			List<OrderEntry> orders = offlineOrderQuery.getResultList(result) ;
			List<OrderpositionsEntry> positions = new ArrayList<OrderpositionsEntry>() ;
			HashMap<String, IAddressContact> distinctAddresses = new HashMap<String, IAddressContact>() ;
			
			int orderIndex = 0 ;
			
			for (OrderEntry orderEntry : orders) {
				collector = new FilterKriteriumCollector() ;
				collector.add(orderPositionQuery.getOrderIdFilter(orderEntry.getId())) ;
				collector.add(orderPositionQuery.getIsIdentFilter()) ;				
				filterCrits = new FilterBlock(collector.asArray(), "AND")  ;
				
				QueryParameters posParams = orderPositionQuery.getDefaultQueryParameters(filterCrits) ;
				posParams.setLimit(Integer.MAX_VALUE) ;
				posParams.setKeyOfSelectedRow(0) ;

				QueryResult positionResult = orderPositionQuery.setQuery(posParams) ;
				List<OrderpositionEntry> posEntries = orderPositionQuery.getResultList(positionResult) ;	
				
				addPositionEntries(positions, orderEntry.getId(), posEntries);	

				try {
					IAddressContact orderAddress = result.getFlrData()[orderIndex].getAddressContact() ;
					distinctAddresses.put(
							orderAddress.getPartnerAddress().getPartnerId().toString() + (
									orderAddress.getContactAddress() != null
										? ("|" +orderAddress.getContactAddress().getPartnerId().toString()) : ""), orderAddress) ;
				} catch(IndexOutOfBoundsException e) {					
				}
				
				++orderIndex ;
			}
			entry.setOrders(orders) ;
			entry.setOrderpositions(positions) ;

			List<OrderAddressContact> resultAddresses = new ArrayList<OrderAddressContact>();
			for (IAddressContact orderAddress : distinctAddresses.values()) {
//				OrderAddressContact newAddress = modelMapper.map(orderAddress, OrderAddressContact.class) ;
				OrderAddressContact newAddress = new OrderAddressContact() ;
				newAddress.setPartnerAddress(modelMapper.map(orderAddress.getPartnerAddress(), OrderAddress.class)) ;
				if(orderAddress.getContactAddress() != null) {
					newAddress.setContactAddress(modelMapper.map(orderAddress.getContactAddress(), OrderAddress.class));
				}
				resultAddresses.add(newAddress) ;
			}
			entry.setAddresses(resultAddresses);
			
		} catch(NamingException e) {
			respondUnavailable(e) ;
			e.printStackTrace() ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
			e.printStackTrace() ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e);
		}		
		
		return entry ;
	}
	
	@GET
	@Path("comments")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public OrderComments getOrderComments (
			@HeaderParam(ParamInHeader.TOKEN) String headerToken,
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.ORDERID) Integer orderId,
			@QueryParam(Param.ORDERCNR) String orderCnr,
			@QueryParam("addInternalComment") Boolean addInternalComment,
			@QueryParam("addExternalComment") Boolean addExternalComment) {
		OrderComments comments = new OrderComments() ;

		try {
			if(null == connectClient(headerToken, userId)) return comments ;
			
			if(!mandantCall.hasModulAuftrag()) {
				respondNotFound() ;
				return comments ;
			}

			if(orderId == null && StringHelper.isEmpty(orderCnr)) {
				respondBadRequestValueMissing(Param.ORDERID);
				return comments ;
			}
			
			AuftragDto auftragDto = null ;
			if(orderId != null) {
				auftragDto = auftragCall.auftragFindByPrimaryKeyOhneExc(orderId) ;
			}
			
			if(auftragDto == null  && !StringHelper.isEmpty(orderCnr)) {
				auftragDto = auftragCall.auftragFindByCnr(orderCnr) ;
			}

			if(auftragDto == null || !auftragDto.getMandantCNr().equals(globalInfo.getTheClientDto().getMandant())) {
				respondNotFound() ;
				return comments ;
			}

			comments.setId(auftragDto.getIId());
			if(addInternalComment == null || addInternalComment) {
				comments.setInternalComment(auftragDto.getXInternerkommentar()) ;
			}
			if(addExternalComment == null || addExternalComment) {
				comments.setExternalComment(auftragDto.getXExternerkommentar()) ;
			}
		} catch(RemoteException e) {
			respondUnavailable(e); 
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
		
		return comments ;
	}
}

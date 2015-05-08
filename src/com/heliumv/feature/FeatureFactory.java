package com.heliumv.feature;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IPartnerCall;

//public class FeatureFactory implements FactoryBean<HvFeature>, ApplicationContextAware {
//TODO: Ohne Type, da sonst enunciate einen Fehler beim Compile meldet :(
// Es hilft auch nicht auf enunciate 1.29 umzustellen
public class FeatureFactory implements FactoryBean, ApplicationContextAware {
	class Constants {
		// Diese Konstanten muessen mit cxf-beans.xml uebereinstimmen
		public final static String CustomerPartlist = "featureCustomerpartlist" ;
		public final static String Default = "featureDefault" ;
	}
	
	private ApplicationContext applicationContext ;
	private String serviceType ;
	
	
	@Autowired
	private IGlobalInfo globalInfo ;

	@Autowired
	private IPartnerCall partnerCall ;
	
//	public HvFeature get(FeatureType feature) {
//		if(FeatureType.CUSTOMER_PARTLIST.equals(feature)) {
//			return new HvCustomerPartlistFeature() ;
//		}
//		
//		return new HvDefaultFeature() ;
//	}
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext ;
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext ;
	}
	
	public boolean isSingleton() {
		return false;
	}
	
	public Class<HvFeature> getObjectType() {
		return HvFeature.class ;
	}
	
    /**
     * @return the location
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType
     *            the location to set
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

	public HvFeature getObject() throws Exception {
		HvFeature service = null ;
		
		if(hasCustomerPartlist()) {
			service = (HvFeature) applicationContext.getBean(Constants.CustomerPartlist) ;
		} else {
			service = (HvFeature) applicationContext.getBean(Constants.Default) ;
		}
		
		return service ;
	}
	
	public boolean hasCustomerPartlist() {
		if(globalInfo.getTheClientDto() == null) return false ;
		return globalInfo.getTheClientDto().getIStatus() != null ;
	}

	/**
	 * Die AnsprechpartnerId ermitteln</br>
	 * <p>Das ist momentan noch nicht sehr sauber, da die PartnerId ja nur
	 * in speziellen F&auml;llen gesetzt ist. Au&szlig;erdem ist es wieder
	 * ein Implementierungsdetail das nach draussen geht. Aber es ist noch
	 * besser, als immer den IStatus zu verwenden.
	 * 
	 * @return null oder die PartnerId des angemeldeten Kunden. 
	 */
	public Integer getAnsprechpartnerId() {
		return globalInfo.getTheClientDto().getIStatus() ;
	}
	
	/**
	 * Die PartnerId aus der AnsprechpartnerId ermitteln</br>
	 * 
	 * @return null, oder die PartnerId des Ansprechpartners
	 * @throws RemoteException
	 * @throws NamingException
	 */ 
	public Integer getPartnerIdFromAnsprechpartnerId() throws RemoteException, NamingException {
		Integer id = getAnsprechpartnerId() ;
		if(id == null) return null ;

		return partnerCall.partnerIdFindByAnsprechpartnerId(id) ;
	}
}

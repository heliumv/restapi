package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IParameterCall;
import com.lp.server.system.service.ParameterFac;
import com.lp.server.system.service.ParametermandantDto;

public class ParameterCall extends BaseCall<ParameterFac> implements IParameterCall {
	public ParameterCall() throws NamingException {
		super(ParameterFacBean) ;
	}

	public boolean isZeitdatenAufErledigteBuchbar() throws RemoteException {
		ParametermandantDto param = getFac()
			.getMandantparameter(Globals.getTheClientDto().getMandant(),
					ParameterFac.KATEGORIE_PERSONAL,
					ParameterFac.PARAMETER_ZEITBUCHUNG_AUF_ERLEDIGTE_MOEGLICH) ;
		return "1".equals(param.getCWert()) ;
	}
	
	public boolean isPartnerSucheWildcardBeidseitig() throws RemoteException {
		ParametermandantDto param = getFac()
				.getMandantparameter(Globals.getTheClientDto().getMandant(),
						ParameterFac.KATEGORIE_PARTNER,
						ParameterFac.PARAMETER_PARTNERSUCHE_WILDCARD_BEIDSEITIG) ;
		return (Boolean) param.getCWertAsObject() ;
	}
}

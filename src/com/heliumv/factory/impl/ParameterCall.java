/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IParameterCall;
import com.lp.server.artikel.service.ArtikelFac;
import com.lp.server.system.service.ParameterFac;
import com.lp.server.system.service.ParametermandantDto;
import com.lp.util.EJBExceptionLP;

public class ParameterCall extends BaseCall<ParameterFac> implements IParameterCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public ParameterCall() {
		super(ParameterFacBean) ;
	}

	public boolean isZeitdatenAufErledigteBuchbar() throws NamingException, RemoteException {
		ParametermandantDto param = getFac()
			.getMandantparameter(globalInfo.getMandant(),
					ParameterFac.KATEGORIE_PERSONAL,
					ParameterFac.PARAMETER_ZEITBUCHUNG_AUF_ERLEDIGTE_MOEGLICH) ;
		return "1".equals(param.getCWert()) ;
	}

	public boolean isZeitdatenAufAngelegteLoseBuchbar() throws NamingException, RemoteException {
		ParametermandantDto param = getFac()
			.getMandantparameter(globalInfo.getMandant(),
					ParameterFac.KATEGORIE_PERSONAL,
					ParameterFac.PARAMETER_ZEITBUCHUNG_AUF_ANGELEGTE_LOSE_MOEGLICH) ;
		return "1".equals(param.getCWert()) ;
	}
	
	public boolean isPartnerSucheWildcardBeidseitig() throws NamingException, RemoteException {
		ParametermandantDto param = getFac()
				.getMandantparameter(globalInfo.getMandant(),
						ParameterFac.KATEGORIE_PARTNER,
						ParameterFac.PARAMETER_PARTNERSUCHE_WILDCARD_BEIDSEITIG) ;
		return (Boolean) param.getCWertAsObject() ;
	}
	
	
	public boolean isKeineAutomatischeMaterialbuchung() throws NamingException, RemoteException {
		ParametermandantDto param = getFac()
				.getMandantparameter(globalInfo.getMandant(),
						ParameterFac.KATEGORIE_FERTIGUNG,
						ParameterFac.PARAMETER_KEINE_AUTOMATISCHE_MATERIALBUCHUNG) ;
		return (Boolean) param.getCWertAsObject() ;
	}
	
	public boolean isBeiLosErledigenMaterialNachbuchen() throws NamingException, RemoteException {
		ParametermandantDto param = getFac()
				.getMandantparameter(globalInfo.getMandant(),
						ParameterFac.KATEGORIE_FERTIGUNG,
						ParameterFac.PARAMETER_BEI_LOS_ERLEDIGEN_MATERIAL_NACHBUCHEN) ;
		return (Boolean) param.getCWertAsObject() ;	
	}
	
	public int getMaximaleLaengeArtikelnummer()  throws NamingException, RemoteException {
		int defaultLaenge = ArtikelFac.MAX_ARTIKEL_ARTIKELNUMMER ;
		
		ParametermandantDto parameter = getFac()
				.getMandantparameter(globalInfo.getMandant(),
						ParameterFac.KATEGORIE_ARTIKEL,
						ParameterFac.PARAMETER_ARTIKEL_MAXIMALELAENGE_ARTIKELNUMMER);
		if (parameter.getCWertAsObject() != null) {
			defaultLaenge = ((Integer) parameter.getCWertAsObject()).intValue();
		}
		
		return defaultLaenge ;
	}
	
	public boolean isArtikelDirektfilterGruppeKlasseStattReferenznummer() throws NamingException, RemoteException {
		ParametermandantDto param = getFac()
				.getMandantparameter(globalInfo.getMandant(),
						ParameterFac.KATEGORIE_ARTIKEL,
						ParameterFac.PARAMETER_DIREKTFILTER_GRUPPE_KLASSE_STATT_REFERENZNUMMER) ;
		return (Boolean) param.getCWertAsObject() ;	
	}
	
	public Integer getGeschaeftsjahr(String mandantCNr) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().getGeschaeftsjahr(mandantCNr) ;
	}
	
	public Integer getGeschaeftsjahr() throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().getGeschaeftsjahr(globalInfo.getMandant()) ;
	}

	public String getMailAdresseAdmin() throws NamingException, RemoteException, EJBExceptionLP {
		ParametermandantDto param = getFac()
				.getMandantparameter(globalInfo.getMandant(),
						ParameterFac.KATEGORIE_VERSANDAUFTRAG,
						ParameterFac.PARAMETER_MAILADRESSE_ADMIN) ;
		return param.getCWert() ;			
	}
}

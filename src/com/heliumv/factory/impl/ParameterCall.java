package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IParameterCall;
import com.lp.server.artikel.service.ArtikelFac;
import com.lp.server.system.service.ParameterFac;
import com.lp.server.system.service.ParametermandantDto;

public class ParameterCall extends BaseCall<ParameterFac> implements IParameterCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public ParameterCall() {
		super(ParameterFacBean) ;
	}

	public boolean isZeitdatenAufErledigteBuchbar() throws NamingException, RemoteException {
		ParametermandantDto param = getFac()
			.getMandantparameter(Globals.getTheClientDto().getMandant(),
					ParameterFac.KATEGORIE_PERSONAL,
					ParameterFac.PARAMETER_ZEITBUCHUNG_AUF_ERLEDIGTE_MOEGLICH) ;
		return "1".equals(param.getCWert()) ;
	}
	
	public boolean isPartnerSucheWildcardBeidseitig() throws NamingException, RemoteException {
		ParametermandantDto param = getFac()
				.getMandantparameter(Globals.getTheClientDto().getMandant(),
						ParameterFac.KATEGORIE_PARTNER,
						ParameterFac.PARAMETER_PARTNERSUCHE_WILDCARD_BEIDSEITIG) ;
		return (Boolean) param.getCWertAsObject() ;
	}
	
	
	public boolean isKeineAutomatischeMaterialbuchung() throws NamingException, RemoteException {
		ParametermandantDto param = getFac()
				.getMandantparameter(Globals.getTheClientDto().getMandant(),
						ParameterFac.KATEGORIE_FERTIGUNG,
						ParameterFac.PARAMETER_KEINE_AUTOMATISCHE_MATERIALBUCHUNG) ;
		return (Boolean) param.getCWertAsObject() ;
	}
	
	public boolean isBeiLosErledigenMaterialNachbuchen() throws NamingException, RemoteException {
		ParametermandantDto param = getFac()
				.getMandantparameter(Globals.getTheClientDto().getMandant(),
						ParameterFac.KATEGORIE_FERTIGUNG,
						ParameterFac.PARAMETER_BEI_LOS_ERLEDIGEN_MATERIAL_NACHBUCHEN) ;
		return (Boolean) param.getCWertAsObject() ;	
	}
	
	public int getMaximaleLaengeArtikelnummer()  throws NamingException, RemoteException {
		int defaultLaenge = ArtikelFac.MAX_ARTIKEL_ARTIKELNUMMER ;
		
		ParametermandantDto parameter = getFac()
				.getMandantparameter(Globals.getTheClientDto().getMandant(),
						ParameterFac.KATEGORIE_ARTIKEL,
						ParameterFac.PARAMETER_ARTIKEL_MAXIMALELAENGE_ARTIKELNUMMER);
		if (parameter.getCWertAsObject() != null) {
			defaultLaenge = ((Integer) parameter.getCWertAsObject()).intValue();
		}
		
		return defaultLaenge ;
	}
	
	public boolean isArtikelDirektfilterGruppeKlasseStattReferenznummer() throws NamingException, RemoteException {
		String mandant = globalInfo.getMandant() ;

		ParametermandantDto param = getFac()
				.getMandantparameter(mandant,
						ParameterFac.KATEGORIE_ARTIKEL,
						ParameterFac.PARAMETER_DIREKTFILTER_GRUPPE_KLASSE_STATT_REFERENZNUMMER) ;
		return (Boolean) param.getCWertAsObject() ;	
	}
}

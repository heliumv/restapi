package com.heliumv.factory.impl;

import java.sql.Date;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IMaschineCall;
import com.lp.server.personal.service.MaschineFac;
import com.lp.server.personal.service.MaschinenVerfuegbarkeitsStundenDto;

public class MaschineCall extends BaseCall<MaschineFac> implements
		IMaschineCall {
	@Autowired
	private IGlobalInfo globalInfo ;

	public MaschineCall() {
		super(MaschineFacBean) ;
	}
	
	public List<MaschinenVerfuegbarkeitsStundenDto> getVerfuegbarkeitStunden(Integer maschineId, Date startDate, int days) throws NamingException {
		return getFac().getVerfuegbarkeitInStunden(maschineId, startDate, days, globalInfo.getTheClientDto()) ;
	}
}

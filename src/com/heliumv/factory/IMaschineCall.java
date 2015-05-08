package com.heliumv.factory;

import java.sql.Date;
import java.util.List;

import javax.naming.NamingException;

import com.lp.server.personal.service.MaschinenVerfuegbarkeitsStundenDto;

public interface IMaschineCall {
	List<MaschinenVerfuegbarkeitsStundenDto> getVerfuegbarkeitStunden(
			Integer maschineId, Date startDate, int days) throws NamingException ;
}

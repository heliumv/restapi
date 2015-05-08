package com.heliumv.api;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heliumv.api.BaseApi.HvErrorCode;

public class HvNamingExceptionMapper implements
		ExceptionMapper<NamingException> {
	private static Logger log = LoggerFactory.getLogger(HvNamingExceptionMapper.class) ;

	@Override
	public Response toResponse(NamingException e) {
		log.info("default-log", e);
		return new ResponseBuilderImpl()
			.header(BaseApi.X_HV_ERROR_CODE, HvErrorCode.NAMING_EXCEPTION.toString())
			.header(BaseApi.X_HV_ERROR_CODE_DESCRIPTION, e.getMessage())
			.status(Response.Status.INTERNAL_SERVER_ERROR).build() ;
	}

}

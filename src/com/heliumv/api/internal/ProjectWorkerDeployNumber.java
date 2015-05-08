package com.heliumv.api.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lp.server.projekt.service.ProjektDto;

public class ProjectWorkerDeployNumber extends AbstractProjectWorker {
	private static Logger log = LoggerFactory.getLogger(ProjectWorkerDeployNumber.class) ;

	@Override
	protected void setup(ProjektDto projectDto, String buildLabel) {
		projectDto.setDeployNumber(buildLabel);				
	}
	
	@Override
	protected void info(ProjektDto projectDto) {
//		System.out.println("Updated project '" + projectDto.getCNr() + "' with build '" + projectDto.getDeployNumber() + "'.") ;
		log.info("Updated project '" + projectDto.getCNr() + "' with build '" + projectDto.getDeployNumber() + "'.") ;			
	}
}

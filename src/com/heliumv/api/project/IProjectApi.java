package com.heliumv.api.project;

import java.util.List;

public interface IProjectApi {
	public List<ProjectEntry> getProjects(
			String userId,
			Integer limit, 
			Integer startIndex,
			String filterCnr, 
			String filterCompany,
			Boolean filterWithCancelled); 
}

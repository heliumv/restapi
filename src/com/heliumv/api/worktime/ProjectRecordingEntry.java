package com.heliumv.api.worktime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProjectRecordingEntry extends DocumentRecordingEntry {
	private Integer projectId ;

	/**
	 * Die anzugebende Projekt-Id. Projekte k&ouml;nnen &uuml;ber die Resource <code>project</code> ermittelt werden.
	 * @return
	 */
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
}

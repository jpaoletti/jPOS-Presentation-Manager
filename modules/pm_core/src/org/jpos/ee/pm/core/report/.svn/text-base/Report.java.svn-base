package org.jpos.ee.pm.core.report;

import java.util.List;

import org.jpos.ee.pm.core.PMCoreObject;

public class Report extends PMCoreObject{
	private String id;
	private String name;
	private String entity_id;
	private List<ReportFilter> filters;
	private List<String> agroupations;
	private List<String> totalizations;
	
	public String toString(){
		return super.toString() + "[id='" + id + "', name='" + name + "']";
	}
	
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	public List<String> getAgroupations() {
		return agroupations;
	}
	public void setAgroupations(List<String> agroupations) {
		this.agroupations = agroupations;
	}
	public List<String> getTotalizations() {
		return totalizations;
	}
	public void setTotalizations(List<String> totalizations) {
		this.totalizations = totalizations;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setFilters(List<ReportFilter> filters) {
		this.filters = filters;
	}
	public List<ReportFilter> getFilters() {
		return filters;
	}
}

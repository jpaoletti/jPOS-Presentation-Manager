package org.jpos.ee.pm.core.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.Field;

public class EntityReport {
	private Map<Object,Entity> entities;
	private Report report;
	private Entity entity;
	private List<Field> filters;
	private List<Field> agroupations;
	private List<Field> totalizations;
	
	public EntityReport(Map<Object,Entity> entities , Report report){
		this.entities = entities;
		this.report = report;		
	}
	
	public void populate() throws BadConstructedReport{
		try {
			if(report != null && entities != null){
				setEntity(entities.get(report.getEntity_id()));
				for(ReportFilter filter : report.getFilters()){
					getFilters().clear();
					getFilters().add(getEntity().getFieldById(filter.getField()));
				}
				if(report.getAgroupations()!= null)
				for(String fid : report.getAgroupations()){
					getAgroupations().clear();
					getAgroupations().add(getEntity().getFieldById(fid));
				}
				if(report.getTotalizations()!=null)
				for(String fid : report.getTotalizations()){
					getTotalizations().clear();
					getTotalizations().add(getEntity().getFieldById(fid));
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadConstructedReport(e.getMessage());			
		}
	}
	
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	public List<Field> getFilters() {
		if(filters == null) filters = new ArrayList<Field>();		
		return filters;
	}
	public void setFilters(List<Field> filters) {
		this.filters = filters;
	}
	public List<Field> getAgroupations() {
		if(agroupations == null) agroupations = new ArrayList<Field>();
		return agroupations;
	}
	public void setAgroupations(List<Field> agroupations) {
		this.agroupations = agroupations;
	}
	public List<Field> getTotalizations() {
		if(totalizations == null) totalizations = new ArrayList<Field>();
		return totalizations;
	}
	public void setTotalizations(List<Field> totalizations) {
		this.totalizations = totalizations;
	}
}

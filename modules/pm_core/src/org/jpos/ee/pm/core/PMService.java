/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2008 Alejandro P. Revilla
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jpos.ee.pm.core;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.jpos.ee.Constants;
import org.jpos.ee.pm.core.report.Report;
import org.jpos.ee.pm.menu.MenuItemLocation;
import org.jpos.ee.pm.menu.MenuItemLocationsParser;
import org.jpos.q2.QBeanSupport;
import org.jpos.util.NameRegistrar;

public class PMService extends QBeanSupport implements Constants{
    protected Map<Object,Entity> entities;
    protected Map<Object,Report> reports;
    protected Map<String,MenuItemLocation> locations;
    private String template;
    private String appversion;
    private boolean loginRequired;
    private boolean ignoreDb;
    private String title;
    private String subtitle;
    private String logo;
    private String contact;
    
    protected void initService() throws Exception {
        getLog().info ("Entity Manager activated");
        EntityParser parser = new EntityParser();
        loadEntities(parser);
        loadReports(parser);
        loadLocations();
        template = cfg.get("template");
        if(template==null || template.compareTo("")==0) template="default";
        
        appversion = cfg.get("appversion");
        if(appversion==null || appversion.compareTo("")==0) appversion="1.0.0";

        title = cfg.get("title");
        if(title==null || title.compareTo("")==0) title="jpos.title";

        subtitle = cfg.get("subtitle");
        if(subtitle==null || subtitle.compareTo("")==0) subtitle="pm.title";

        logo = cfg.get("logo");
        if(logo==null || logo.compareTo("")==0) logo="logo.png";

        contact = cfg.get("contact");
        if(contact==null || contact.compareTo("")==0) contact="mailto:jpaoletti@angras.com.ar";

        try {
			setLoginRequired(cfg.getBoolean("login-required")); 
		} catch (Exception e) {
			setLoginRequired(true);
		}
		
        try {
			setIgnoreDb(cfg.getBoolean("ignore-db")); 
		} catch (Exception e) {
			setIgnoreDb(false);
		}

		if(loginRequired) getLog().info("Login Required");
		else getLog().info("Login Not Required");
        
        
        NameRegistrar.register (getCustomName(), this);
    }

	private void loadLocations() {
		MenuItemLocationsParser parser = new MenuItemLocationsParser("cfg/pm.locations.xml");
		locations = parser.getLocations();
		if(locations == null || locations.size()==0)
			getLog().warn("There is no location defined!");
	}

	public static String getCustomName() {
		return "presentation-manager-ee";
	}
    
	/**Encapsulate a String that is going to be visualized by default (without a Converter)
	 * @param s The String
	 * @return The wrapped String*/
    public String visualizationWrapper(String s){
    	return s;
    }

	private void loadReports(EntityParser parser) throws Exception {
		Map<Object,Report> m = new HashMap<Object,Report>();
        String[] ss = cfg.getAll ("report");
        for (Integer i=0; i<ss.length; i++) {
            Report r = parser.parseReportFile(ss[i]);
            m.put (r.getId(), r);
            m.put (i, r);
            r.setService(this);
            getLog().info (r.toString());
        }
        reports = m;
	}

	private void loadEntities(EntityParser parser) throws FileNotFoundException {
		Map<Object,Entity> m = new HashMap<Object,Entity>();
        String[] ss = cfg.getAll ("entity");
        for (Integer i=0; i<ss.length; i++) {
            Entity e = parser.parseEntityFile(ss[i]);
            m.put (e.getId(), e);
            m.put (i, e);
            e.setService(this);
            getLog().info (e.toString());
        }
        entities = m;
	}
    
	public Map<Object, Report> getReports() {
		return reports;
	}

	public Map<Object,Entity> getEntities() {
        return entities;
    }
    public Entity getEntity (String id) {
        Entity e = getEntities().get (id);
        if(e.getExtendz() != null && e.getExtendzEntity() == null)
        	e.setExtendzEntity(this.getEntity(e.getExtendz()));
		return e;
    }
	public String getTemplate() {
		return template;
	}
	
	public String getAppversion(){
		return appversion;
	}
	
	public MenuItemLocation getLocation(String id){
		return locations.get(id);
	}

	/**
	 * @param loginRequired the loginRequired to set
	 */
	public void setLoginRequired(boolean loginRequired) {
		this.loginRequired = loginRequired;
	}

	/**
	 * @return the loginRequired
	 */
	public boolean isLoginRequired() {
		return loginRequired;
	}

	/**
	 * @param ignoreDb the ignoreDb to set
	 */
	public void setIgnoreDb(boolean ignoreDb) {
		this.ignoreDb = ignoreDb;
	}

	/**
	 * @return the ignoreDb
	 */
	public boolean ignoreDb() {
		return ignoreDb;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	
}
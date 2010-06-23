/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2010 Alejandro P. Revilla
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jpos.ee.Constants;
import org.jpos.ee.pm.core.monitor.Monitor;
import org.jpos.ee.pm.menu.MenuItemLocation;
import org.jpos.ee.pm.menu.MenuItemLocationsParser;
import org.jpos.q2.QBeanSupport;
import org.jpos.util.LogEvent;
import org.jpos.util.Logger;
import org.jpos.util.NameRegistrar;

/**Presentation Manager service bean.
  * 
  * @author jpaoletti jeronimo.paoletti@gmail.com
  * http://github.com/jpaoletti/jPOS-Presentation-Manager
  * */
public class PMService extends QBeanSupport implements Constants{
    protected static final String TAB = "    ";
    protected static final String ERR = " ==>";
    protected Map<Object,Entity> entities;
    protected Map<String,MenuItemLocation> locations;
    private Map<Object, Monitor> monitors;
    private String template;
    private String appversion;
    private boolean loginRequired;
    private String title;
    private String subtitle;
    private String logo;
    private String contact;
    private String defaultDataAccess;
    private PersistenceManager persistenceManager;
    private boolean error;
    
    protected void initService() throws Exception {
        error = false;
        PMLogger.setLog(getLog());
        PMLogger.setDebug(cfg.getBoolean("debug"));

        LogEvent evt = getLog().createInfo();
        evt.addMessage("Presentation Manager activated");
        try {
            EntityParser parser = new EntityParser();
            loadEntities(evt, parser);
            loadMonitors(evt, parser);
            loadLocations(evt);
            evt.addMessage(TAB + "Configuration");
            template = cfg.get("template");
            if (template == null || template.compareTo("") == 0) {
                template = "default";

            }
            evt.addMessage(TAB + TAB + "Template: " + template);

            defaultDataAccess = cfg.get("default-data-access", "org.jpos.ee.pm.core.DataAccessDB");
            evt.addMessage(TAB + TAB + "Default Data Access: " + defaultDataAccess);

            appversion = cfg.get("appversion");
            if (appversion == null || appversion.compareTo("") == 0) {
                appversion = "1.0.0";

            }
            evt.addMessage(TAB + TAB + "Application version: " + appversion);

            title = cfg.get("title");
            if (title == null || title.compareTo("") == 0) {
                title = "jpos.title";

            }
            evt.addMessage(TAB + TAB + "Title: " + title);

            subtitle = cfg.get("subtitle");
            if (subtitle == null || subtitle.compareTo("") == 0) {
                subtitle = "pm.title";

            }
            evt.addMessage(TAB + TAB + "Subtitle: " + subtitle);

            contact = cfg.get("contact");
            if (contact == null || contact.compareTo("") == 0) {
                contact = "mailto:jpaoletti@angras.com.ar";

            }
            evt.addMessage(TAB + TAB + "Contact: " + contact);

            try {
                setLoginRequired(cfg.getBoolean("login-required"));
            } catch (Exception e) {
                setLoginRequired(true);
            }
            evt.addMessage(TAB + TAB + "Login Required: " + loginRequired);

            setPersistenceManager((PersistenceManager) Class.forName(cfg.get("persistence-manager", "org.jpos.ee.pm.core.DBPersistenceManager")).newInstance());
            evt.addMessage(TAB + TAB + "Persistance Manager: " + getPersistenceManager().getClass().getName());
        } catch (Exception exception) {
            getLog().error(exception);
            error = true;
        }
        if(!error){
            NameRegistrar.register (getCustomName(), this);
        }else{
            evt.addMessage("error","One or more errors were found. Unable to start jPOS-PM");
        }
        Logger.log(evt);
    }

    private void loadMonitors(LogEvent evt, EntityParser parser) throws FileNotFoundException {
        evt.addMessage(TAB+"Monitors");
        Map<Object,Monitor> result = new HashMap<Object,Monitor>();
        String[] ss = cfg.getAll ("monitor");
        for (Integer i=0; i<ss.length; i++) {
            try {
                Monitor m = parser.parseMonitorFile(ss[i]);
                result.put(m.getId(), m);
                result.put(i, m);
                m.setService(this);
                m.getSource().init();
                Thread thread = new Thread(m);
                m.setThread(thread);
                thread.start();
                evt.addMessage(TAB + TAB + m.getId());
            } catch (Exception exception) {
                getLog().error(exception);
                evt.addMessage(TAB + TAB + ERR+ "Error loading "+ss[i]);
                error = true;
            }
        }
        setMonitors(result);
    }

    private void loadLocations(LogEvent evt) {
        MenuItemLocationsParser parser = new MenuItemLocationsParser(evt, "cfg/pm.locations.xml");
        locations = parser.getLocations();
        if(locations == null || locations.size()==0){
            evt.addMessage(TAB+TAB+ERR+"No location defined!");
            error = true;
        }
        if(parser.hasError()) error = true;
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

    private void loadEntities(LogEvent evt, EntityParser parser) throws FileNotFoundException {
        evt.addMessage(TAB+"Entities");
        Map<Object,Entity> m = new HashMap<Object,Entity>();
        String[] ss = cfg.getAll ("entity");
        for (Integer i=0; i<ss.length; i++) {
            try {
                Entity e = parser.parseEntityFile(ss[i]);
                try{
                    Class.forName(e.getClazz());
                    m.put(e.getId(), e);
                    m.put(i, e);
                    e.setService(this);
                    evt.addMessage(String.format(TAB + TAB + "[%-30s] %s", e.getId(), e.getClazz()));
                } catch (ClassNotFoundException cnte){
                    evt.addMessage(TAB + TAB + ERR +String.format("Class '%s' not found in %s",e.getClazz(), ss[i]));
                    error = true;
                }
            } catch (Exception exception) {
                getLog().error(exception);
                evt.addMessage(TAB + TAB +ERR+ "Error loading "+ss[i]);
                error = true;
            }
        }
        entities = m;
    }
    
    protected List<Entity> weakEntities(Entity e) {
        List<Entity> res = new ArrayList<Entity>();
        for(Entity entity : getEntities().values()){
            if(entity.getOwner() != null && entity.getOwner().getEntityId().compareTo(e.getId())==0){
                res.add(entity);
            }
        }
        if(res.isEmpty()) return null; else return res;
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
    public Monitor getMonitor (String id) {
        return getMonitors().get (id);
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

    /**
     * @param monitors the monitors to set
     */
    public void setMonitors(Map<Object, Monitor> monitors) {
        this.monitors = monitors;
    }

    /**
     * @return the monitors
     */
    public Map<Object, Monitor> getMonitors() {
        return monitors;
    }

    /**
     * @param defaultDataAccess the defaultDataAccess to set
     */
    public void setDefaultDataAccess(String defaultDataAccess) {
        this.defaultDataAccess = defaultDataAccess;
    }

    /**
     * @return the defaultDataAccess
     */
    public String getDefaultDataAccess() {
        return defaultDataAccess;
    }

    /**
     * @param persistanceManager the persistanceManager to set
     */
    public void setPersistenceManager(PersistenceManager persistanceManager) {
        this.persistenceManager = persistanceManager;
    }

    /**
     * @return the persistanceManager
     */
    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }
}
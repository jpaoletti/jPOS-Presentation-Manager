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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.jpos.core.Configuration;
import org.jpos.ee.pm.core.monitor.Monitor;
import org.jpos.ee.pm.menu.MenuItemLocation;
import org.jpos.ee.pm.menu.MenuItemLocationsParser;
import org.jpos.util.Log;
import org.jpos.util.LogEvent;
import org.jpos.util.Logger;

/**
 *
 * @author jpaoletti
 */
public class PresentationManager extends Observable {
    public static String HASH = "abcde54321poiuy96356abcde54321poiuy96356";
    // Singleton
    public static PresentationManager pm;
    protected static final String TAB = "    ";
    protected static final String ERR = " ==>";
    protected Map<Object, Entity> entities;
    protected Map<String, MenuItemLocation> locations;
    private Map<Object, Monitor> monitors;
    private String template;
    private String appversion;
    private boolean loginRequired;
    private String title;
    private String subtitle;
    private String contact;
    private String defaultDataAccess;
    private PersistenceManager persistenceManager;
    private boolean error;
    private Log log;
    private boolean debug;
    private PMService service;

    public boolean initialize(Configuration cfg, Log log, PMService service) {
        notifyObservers();
        error = false;
        this.log = log;
        this.debug = cfg.getBoolean("debug");
        this.service = service;

        LogEvent evt = getLog().createInfo();
        evt.addMessage("Presentation Manager activated");
        try {
            EntityParser parser = new EntityParser();
            loadEntities(cfg, evt, parser);
            loadMonitors(cfg, evt, parser);
            loadLocations(evt);
            evt.addMessage(TAB + "Configuration");

            template = cfg.get("template", "default");
            evt.addMessage(TAB + TAB + "Template: " + template);

            defaultDataAccess = cfg.get("default-data-access", "org.jpos.ee.pm.core.DataAccessVoid");
            evt.addMessage(TAB + TAB + "Default Data Access: " + defaultDataAccess);

            appversion = cfg.get("appversion", "1.0.0");
            evt.addMessage(TAB + TAB + "Application version: " + appversion);

            title = cfg.get("title", "pm.title");
            evt.addMessage(TAB + TAB + "Title: " + title);

            subtitle = cfg.get("subtitle", "pm.subtitle");
            evt.addMessage(TAB + TAB + "Subtitle: " + subtitle);

            contact = cfg.get("contact", "mailto:jpaoletti@angras.com.ar");
            evt.addMessage(TAB + TAB + "Contact: " + contact);

            loginRequired = cfg.getBoolean("login-required", true);
            evt.addMessage(TAB + TAB + "Login Required: " + loginRequired);

            setPersistenceManager((PersistenceManager) Class.forName(cfg.get("persistence-manager", "org.jpos.ee.pm.core.PersistenceManagerVoid")).newInstance());
            evt.addMessage(TAB + TAB + "Persistance Manager: " + getPersistenceManager().getClass().getName());
        } catch (Exception exception) {
            getLog().error(exception);
            error = true;
        }
        if (error) {
            evt.addMessage("error", "One or more errors were found. Unable to start jPOS-PM");
        }
        Logger.log(evt);
        return !error;
    }

    public void debug(Object invoquer, Object o) {
        if(!debug) return;
        LogEvent evt = getLog().createDebug();
        evt.addMessage("["+invoquer.getClass().getName()+"]");
        evt.addMessage(o);
        Logger.log(evt);
    }

    private void loadMonitors(Configuration cfg, LogEvent evt, EntityParser parser) {
        evt.addMessage(TAB + "Monitors");
        Map<Object, Monitor> result = new HashMap<Object, Monitor>();
        String[] ss = cfg.getAll("monitor");
        for (Integer i = 0; i < ss.length; i++) {
            try {
                Monitor m = parser.parseMonitorFile(ss[i]);
                result.put(m.getId(), m);
                result.put(i, m);
                m.getSource().init();
                Thread thread = new Thread(m);
                m.setThread(thread);
                thread.start();
                evt.addMessage(TAB + TAB + m.getId());
            } catch (Exception exception) {
                getLog().error(exception);
                evt.addMessage(TAB + TAB + ERR + "Error loading " + ss[i]);
                error = true;
            }
        }
        monitors = result;
    }

    private void loadLocations(LogEvent evt) {
        MenuItemLocationsParser parser = new MenuItemLocationsParser(evt, "cfg/pm.locations.xml");
        locations = parser.getLocations();
        if (locations == null || locations.size() == 0) {
            evt.addMessage(TAB + TAB + ERR + "No location defined!");
            error = true;
        }
        if (parser.hasError()) {
            error = true;
        }
    }

    /**Encapsulate a String that is going to be visualized by default (without a Converter)
     * @param s The String
     * @return The wrapped String*/
    public String visualizationWrapper(String s) {
        return getService().visualizationWrapper(s);
    }

    private void loadEntities(Configuration cfg, LogEvent evt, EntityParser parser) {
        evt.addMessage(TAB + "Entities");
        if (entities == null) {
            entities = new HashMap<Object, Entity>();
        } else {
            entities.clear();
        }
        String[] ss = cfg.getAll("entity");
        for (Integer i = 0; i < ss.length; i++) {
            try {
                Entity e = parser.parseEntityFile(ss[i]);
                try {
                    Class.forName(e.getClazz());
                    entities.put(e.getId(), e);
                    entities.put(i, e);
                    evt.addMessage(String.format(TAB + TAB + "[%-30s] %s", e.getId(), e.getClazz()));
                } catch (ClassNotFoundException cnte) {
                    evt.addMessage(TAB + TAB + ERR + String.format("Class '%s' not found in %s", e.getClazz(), ss[i]));
                    error = true;
                }
            } catch (Exception exception) {
                getLog().error(exception);
                evt.addMessage(TAB + TAB + ERR + "Error loading " + ss[i]);
                error = true;
            }
        }
    }

    protected List<Entity> weakEntities(Entity e) {
        List<Entity> res = new ArrayList<Entity>();
        for (Entity entity : getEntities().values()) {
            if (entity.getOwner() != null && entity.getOwner().getEntityId().compareTo(e.getId()) == 0) {
                res.add(entity);
            }
        }
        if (res.isEmpty()) {
            return null;
        } else {
            return res;
        }
    }

    public Entity getEntity(String id) {
        Entity e = getEntities().get(id);
        if (e.getExtendz() != null && e.getExtendzEntity() == null) {
            e.setExtendzEntity(this.getEntity(e.getExtendz()));
        }
        return e;
    }

    public MenuItemLocation getLocation(String id) {
        return locations.get(id);
    }

    public Monitor getMonitor(String id) {
        return getMonitors().get(id);
    }

    /**Create and fill a new Entity Container */
    public EntityContainer newEntityContainer(String sid) {
        Entity e = lookupEntity(sid);
        if (e == null) {
            return null;
        }
        e.setWeaks(weakEntities(e));
        return new EntityContainer(e, HASH);
    }

    /**Looks for an Entity with the given id*/
    private Entity lookupEntity(String sid) {
        for (Integer i = 0; i < getEntities().size(); i++) {
            Entity e = getEntities().get(i);
            if (e != null && sid.compareTo(EntityContainer.buildId(HASH, e.getId())) == 0) {
                return getEntity(e.getId());
            }
        }
        return null;
    }


    /* Getters */
    public String getAppversion() {
        return appversion;
    }

    public String getContact() {
        return contact;
    }

    public String getDefaultDataAccess() {
        return defaultDataAccess;
    }

    public Map<Object, Entity> getEntities() {
        return entities;
    }

    public Map<String, MenuItemLocation> getLocations() {
        return locations;
    }

    public Log getLog() {
        return log;
    }

    public boolean isLoginRequired() {
        return loginRequired;
    }

    public Map<Object, Monitor> getMonitors() {
        return monitors;
    }

    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTemplate() {
        return template;
    }

    public String getTitle() {
        return title;
    }

    public static PresentationManager getPm() {
        return pm;
    }

    public PMService getService() {
        return service;
    }

    /* Loggin helpers*/

    /**Generate an info entry on the local logger*/
    public void info(Object o){
        LogEvent evt = getLog().createInfo();
        evt.addMessage(o);
        Logger.log(evt);
    }

    /**Generate a warn entry on the local logger*/
    public void warn(Object o){
        LogEvent evt = getLog().createWarn();
        evt.addMessage(o);
        Logger.log(evt);
    }

    /**Generate an error entry on the local logger*/
    public void error(Object o){
        LogEvent evt = getLog().createError();
        evt.addMessage(o);
        Logger.log(evt);
    }

    /* Helpers for bean management */

    /**Getter for an object property value as String
     * @param obj The object
     * @param propertyName The property
     * @return The value of the property of the object as string
     * */
    public String getAsString(Object obj, String propertyName){
        Object o = get(obj, propertyName);
        if(o != null) return o.toString(); else return "";
    }

    /**Getter for an object property value
     * @param obj The object
     * @param propertyName The property
     * @return The value of the property of the object
     * */
    public Object get (Object obj, String propertyName) {
        try {
            if (obj != null && propertyName != null)
                return PropertyUtils.getNestedProperty (obj, propertyName);
        } catch (NullPointerException e) {
            // OK to happen
        } catch (NestedNullException e) {
            // Hmm... that's fine too
        } catch (Exception e) {
            // Now I don't like it.
            error(e);
            return "-undefined-";
        }
        return null;
    }


    /**Setter for an object property value
     * @param obj The object
     * @param name The property name
     * @param value The value to set
     * */
    public void set (Object obj, String name, Object value) {
        try {
            PropertyUtils.setNestedProperty (obj, name, value);
        } catch (Exception e) {
            error(e);
        }
    }

    public Object newObjectOf(String clazz) {
        try {
            return Class.forName(clazz).newInstance();
        } catch (Exception e) {
            error(e);
            return null;
        }
    }
}
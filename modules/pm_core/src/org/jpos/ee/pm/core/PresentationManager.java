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

import java.util.*;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.jpos.core.Configuration;
import org.jpos.ee.pm.converter.*;
import org.jpos.ee.pm.core.monitor.Monitor;
import org.jpos.ee.pm.menu.*;
import org.jpos.ee.pm.parser.*;
import org.jpos.util.*;

/**
 *
 * @author jpaoletti
 */
public class PresentationManager extends Observable {

    /**  Hash value for parameter encrypt  */
    public static String HASH = "abcde54321poiuy96356abcde54321poiuy96356";
    /** Singleton */
    public static PresentationManager pm;
    private static Long sessionIdSeed = 0L;
    private Configuration cfg;
    private static final String TAB = "    ";
    private static final String ERR = " ==>";
    private Map<Object, Entity> entities;
    private Map<String, MenuItemLocation> locations;
    private Map<Object, Monitor> monitors;
    private List<ExternalConverters> externalConverters;
    private PersistenceManager persistenceManager;
    private boolean error;
    private Log log;
    private PMService service;
    private final Map<String, PMSession> sessions = new HashMap<String, PMSession>();
    private Timer sessionChecker;

    /**
     * Initialize the Presentation Manager
     * @param cfg Configuration from bean
     * @param log Logger from bean
     * @param service Bean
     * @return
     */
    public boolean initialize(Configuration cfg, Log log, PMService service) {
        notifyObservers();
        this.cfg = cfg;
        error = false;
        this.log = log;
        this.service = service;

        LogEvent evt = getLog().createInfo();
        evt.addMessage("startup", "Presentation Manager activated");
        try {
            evt.addMessage(TAB + "<configuration>");

            try {
                Class.forName(getDefaultDataAccess());
                logItem(evt, "Default Data Access", getDefaultDataAccess(), "*");
            } catch (Exception e) {
                logItem(evt, "Default Data Access", getDefaultDataAccess(), "?");
            }

            logItem(evt, "Template", getTemplate(), "*");
            logItem(evt, "Menu", getMenu(), "*");
            logItem(evt, "Application version", getAppversion(), "*");
            logItem(evt, "Title", getTitle(), "*");
            logItem(evt, "Subtitle", getSubtitle(), "*");
            logItem(evt, "Contact", getContact(), "*");
            logItem(evt, "Login Required", Boolean.toString(isLoginRequired()), "*");
            logItem(evt, "Default Converter", getDefaultConverterClass(), "*");

            final String tmp = cfg.get("persistence-manager", "org.jpos.ee.pm.core.PersistenceManagerVoid");
            try {
                setPersistenceManager((PersistenceManager) newInstance(tmp));
                logItem(evt, "Persistance Manager", getPersistenceManager().getClass().getName(), "*");
            } catch (Exception e) {
                error = true;
                logItem(evt, "Persistance Manager", tmp, "?");
            }
            evt.addMessage(TAB + "<configuration>");

            loadEntities(cfg, evt);
            loadMonitors(cfg, evt);
            loadConverters(cfg, evt);
            loadLocations(evt);
            createSessionChecker();
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

    public String getTitle() {
        return cfg.get("title", "pm.title");
    }

    public String getTemplate() {
        return cfg.get("template", "default");
    }

    public boolean isDebug() {
        return cfg.getBoolean("debug");
    }

    public String getAppversion() {
        return cfg.get("appversion", "1.0.0");
    }

    /**
     * If debug flag is active, create a debug information log
     * 
     * @param invoquer The invoquer of the debug
     * @param o Object to log
     */
    public void debug(Object invoquer, Object o) {
        if (!isDebug()) {
            return;
        }
        LogEvent evt = getLog().createDebug();
        evt.addMessage("[" + invoquer.getClass().getName() + "]");
        evt.addMessage(o);
        Logger.log(evt);
    }

    protected String getDefaultConverterClass() {
        return getCfg().get("default-converter");
    }

    private void loadMonitors(Configuration cfg, LogEvent evt) {
        PMParser parser = new MonitorParser();
        evt.addMessage(TAB + "<monitors>");
        Map<Object, Monitor> result = new HashMap<Object, Monitor>();
        String[] ss = cfg.getAll("monitor");
        for (Integer i = 0; i < ss.length; i++) {
            try {
                Monitor m = (Monitor) parser.parseFile(ss[i]);
                result.put(m.getId(), m);
                result.put(i, m);
                m.getSource().init();
                Thread thread = new Thread(m);
                m.setThread(thread);
                thread.start();
                logItem(evt, m.getId(), m.getSource().getClass().getName(), "*");
            } catch (Exception exception) {
                getLog().error(exception);
                logItem(evt, ss[i], null, "!");
            }
        }
        monitors = result;
        evt.addMessage(TAB + "</monitors>");
    }

    /**
     * Formatting helper for startup
     * @param evt The event
     * @param s1 Text
     * @param s2 Extra description
     * @param symbol Status symbol
     */
    public static void logItem(LogEvent evt, String s1, String s2, String symbol) {
        evt.addMessage(String.format("%s%s(%s) %-25s %s", TAB, TAB, symbol, s1, (s2 != null) ? s2 : ""));
    }

    private void loadLocations(LogEvent evt) {
        evt.addMessage(TAB + "<locations>");
        MenuItemLocationsParser parser = new MenuItemLocationsParser(evt, "cfg/pm.locations.xml");
        locations = parser.getLocations();
        if (locations == null || locations.isEmpty()) {
            evt.addMessage(TAB + TAB + ERR + "No location defined!");
            error = true;
        }
        if (parser.hasError()) {
            error = true;
        }
        evt.addMessage(TAB + "</locations>");
    }

    private void loadEntities(Configuration cfg, LogEvent evt) {
        EntityParser parser = new EntityParser();
        evt.addMessage(TAB + "<entities>");
        if (entities == null) {
            entities = new HashMap<Object, Entity>();
        } else {
            entities.clear();
        }
        String[] ss = cfg.getAll("entity");
        for (Integer i = 0; i < ss.length; i++) {
            try {
                Entity e = (Entity) parser.parseFile(ss[i]);
                try {
                    Class.forName(e.getClazz());
                    entities.put(e.getId(), e);
                    entities.put(i, e);
                    if (e.isWeak()) {
                        logItem(evt, e.getId(), e.getClazz(), "\u00b7");
                    } else {
                        logItem(evt, e.getId(), e.getClazz(), "*");
                    }

                } catch (ClassNotFoundException cnte) {
                    logItem(evt, e.getId(), e.getClazz(), "?");
                    error = true;
                }
            } catch (Exception exception) {
                getLog().error(exception);
                logItem(evt, ss[i], "???", "!");
                error = true;
            }
        }
        evt.addMessage(TAB + "</entities>");
    }

    /**
     * Return the list of weak entities of the given entity.
     * @param e The strong entity
     * @return The list of weak entities
     */
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

    /**
     * Return the entity of the given id
     * @param id Entity id
     * @return The entity
     */
    public Entity getEntity(String id) {
        Entity e = getEntities().get(id);
        if (e == null) {
            return null;
        }
        if (e.getExtendz() != null && e.getExtendzEntity() == null) {
            e.setExtendzEntity(this.getEntity(e.getExtendz()));
        }
        return e;
    }

    /**
     * Return the location of the given id
     * @param id The location id
     * @return The MenuItemLocation
     */
    public MenuItemLocation getLocation(String id) {
        return locations.get(id);
    }

    /**
     * Return the monitor of the given id
     * @param id The monitor id
     * @return The monitor
     */
    public Monitor getMonitor(String id) {
        return getMonitors().get(id);
    }

    /**Create and fill a new Entity Container
     * @param id Entity id
     * @return The container
     */
    public EntityContainer newEntityContainer(String id) {
        Entity e = lookupEntity(id);
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
    /**
     * Getter for contact
     * @return
     */
    public String getContact() {
        return cfg.get("contact", "jeronimo.paoletti@gmail.com");
    }

    /**
     * Getter for default data access
     * @return
     */
    public String getDefaultDataAccess() {
        return cfg.get("default-data-access", "org.jpos.ee.pm.core.DataAccessVoid");
    }

    /**
     * Getter for entities map
     * @return
     */
    public Map<Object, Entity> getEntities() {
        return entities;
    }

    /**
     * Getter for location map
     * @return
     */
    public Map<String, MenuItemLocation> getLocations() {
        return locations;
    }

    /**
     * Getter for log
     * @return
     */
    public Log getLog() {
        return log;
    }

    /**
     * Getter for login required
     * @return
     */
    public boolean isLoginRequired() {
        return cfg.getBoolean("login-required", true);
    }

    /**
     * Getter for monitor map
     * @return
     */
    public Map<Object, Monitor> getMonitors() {
        return monitors;
    }

    /**
     * Getter for persistanteManager
     * @return
     */
    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }

    /**
     * Setter for persistenceManager
     * @param persistenceManager
     */
    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    /**
     * Getter for singleton pm
     * @return
     */
    public static PresentationManager getPm() {
        return pm;
    }

    /**
     * Getter for bean 
     * @return
     */
    public PMService getService() {
        return service;
    }

    /* Loggin helpers*/
    /**
     * Generate an info entry on the local logger
     * @param o Object to log
     */
    public void info(Object o) {
        LogEvent evt = getLog().createInfo();
        evt.addMessage(o);
        Logger.log(evt);
    }

    /**Generate a warn entry on the local logger
     * @param o Object to log
     */
    public void warn(Object o) {
        LogEvent evt = getLog().createWarn();
        evt.addMessage(o);
        Logger.log(evt);
    }

    /**Generate an error entry on the local logger
     * @param o Object to log
     */
    public void error(Object o) {
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
    public String getAsString(Object obj, String propertyName) {
        Object o = get(obj, propertyName);
        if (o != null) {
            return o.toString();
        } else {
            return "";
        }
    }

    /**Getter for an object property value
     * @param obj The object
     * @param propertyName The property
     * @return The value of the property of the object
     * */
    public Object get(Object obj, String propertyName) {
        try {
            if (obj != null && propertyName != null) {
                return PropertyUtils.getNestedProperty(obj, propertyName);
            }
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
    public void set(Object obj, String name, Object value) {
        try {
            PropertyUtils.setNestedProperty(obj, name, value);
        } catch (Exception e) {
            error(e);
        }
    }

    /**
     * Creates a new instance object of the given class.
     * @param clazz The Class of the new Object
     * @return The new Object or null on any error.
     */
    public Object newInstance(String clazz) {
        try {
            return getService().getFactory().newInstance(clazz);
        } catch (Exception e) {
            error(e);
            return null;
        }
    }

    private void loadConverters(Configuration cfg, LogEvent evt) {
        PMParser parser = new ExternalConverterParser();
        evt.addMessage(TAB + "<external-converters>");
        externalConverters = new ArrayList<ExternalConverters>();
        String[] ss = cfg.getAll("external-converters");
        for (Integer i = 0; i < ss.length; i++) {
            try {
                ExternalConverters ec = (ExternalConverters) parser.parseFile(ss[i]);
                externalConverters.add(ec);
                logItem(evt, ss[i], null, "*");
            } catch (Exception exception) {
                getLog().error(exception);
                logItem(evt, ss[i], null, "!");
            }
        }
        evt.addMessage(TAB + "</external-converters>");
    }

    public Converter findExternalConverter(String id) {
        for (ExternalConverters ecs : externalConverters) {
            ConverterWrapper w = ecs.getWrapper(id);
            if (w != null) {
                return w.getConverter();
            }
        }
        return null;
    }

    /**
     * Creates a new session with the given id
     * @param sessionId The new session id. Must be unique.
     * @throws PMException on already defined session
     * @return New session
     */
    public PMSession registerSession(String sessionId) throws PMException {
        synchronized (sessions) {
            if (sessions.containsKey(sessionId)) {
                throw new PMException("Session already defined");
            }
            sessions.put(sessionId, new PMSession(sessionId));
            return getSession(sessionId);
        }
    }

    /**
     * Return the session for the given id
     * @param sessionId The id of the wanted session
     * @return The session
     */
    public PMSession getSession(String sessionId) {
        final PMSession s = sessions.get(sessionId);
        if (s != null) {
            s.setLastAccess(new Date());
        }
        return s;
    }

    /**
     * Getter for the session map.
     * @return Sessions
     */
    public Map<String, PMSession> getSessions() {
        return sessions;
    }

    /**
     * Removes the given id session 
     */
    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public Configuration getCfg() {
        return cfg;
    }

    public String getSubtitle() {
        return cfg.get("subtitle", "pm.subtitle");
    }

    private void createSessionChecker() {
        final Long timeout = cfg.getLong("session-timeout", 60 * 60) * 1000;
        final int interval = cfg.getInt("session-check-interval", 60 * 5) * 1000;
        sessionChecker = new Timer();
        sessionChecker.schedule(new TimerTask() {

            @Override
            public void run() {
                synchronized (sessions) {
                    List<String> toRemove = new ArrayList<String>();
                    for (Map.Entry<String, PMSession> entry : sessions.entrySet()) {
                        if (entry.getValue().getLastAccess().getTime() + timeout < System.currentTimeMillis()) {
                            toRemove.add(entry.getKey());
                        }
                    }
                    for (String session : toRemove) {
                        removeSession(session);
                    }
                }
            }
        }, 0, interval);
    }

    public String getCopyright() {
        return cfg.get("copyright", "jpos.org");
    }

    public String getMenu() {
        return cfg.get("menu", "cfg/pm.menu.xml");
    }

    public static synchronized String newSessionId() {
        sessionIdSeed++;
        return sessionIdSeed.toString();
    }

    /**
     * Returns the internacionalized string for the given key
     */
    public static String getMessage(String key, Object... params) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("org.jpos.ee.ApplicationResource");
            String string = bundle.getString(key);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    String param = (params[i] == null) ? "" : params[i].toString();
                    string = string.replaceAll("\\{" + i + "\\}", param);
                }
            }
            return string;
        } catch (Exception e) {
            return key;
        }
    }

    public Converter getDefaultConverter() {
        try {
            if (getDefaultConverterClass() != null && !"".equals(getDefaultConverterClass().trim())) {
                return (Converter) newInstance(getDefaultConverterClass());
            }
        } catch (Exception e) {
        }
        return null;
    }
}

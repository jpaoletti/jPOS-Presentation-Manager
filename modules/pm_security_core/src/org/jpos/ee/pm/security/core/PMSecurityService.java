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
package org.jpos.ee.pm.security.core;

import org.jpos.ee.pm.core.PMContext;
import org.jpos.q2.QBeanSupport;
import org.jpos.util.NameRegistrar;
import org.jpos.util.NameRegistrar.NotFoundException;

public class PMSecurityService extends QBeanSupport {
    private PMSecurityConnector connector;
    
    public static PMSecurityService getService(){
        try {
            return (PMSecurityService) NameRegistrar.get(getCustomName());
        } catch (NotFoundException e) {
            return null;
        }
    }
    
    protected void initService() throws Exception {
        getLog().info ("Security Manager activated");
        try {
            connector = (PMSecurityConnector) getFactory().newInstance(cfg.get("connector"));
            connector.setService(this);
        } catch (Exception e) {
            getLog().error("Cannot load security connector", e);
        }
        NameRegistrar.register (getCustomName(), this);
    }
    
    public static String getCustomName() {
        return "security-manager";
    }

    /**
     * @param connector the connector to set
     */
    public void setConnector(PMSecurityConnector connector) {
        this.connector = connector;
    }

    /**
     * @return the connector
     */
    public PMSecurityConnector getConnector(PMContext ctx) {
        if(connector != null) connector.setContext(ctx);
        return connector;
    }
}

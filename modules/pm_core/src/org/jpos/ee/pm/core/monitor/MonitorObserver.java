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
package org.jpos.ee.pm.core.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import org.jpos.ee.pm.core.PresentationManager;

/** This class is an observer of the monitor.
 * 
 * @author jpaoletti jeronimo.paoletti@gmail.com
 * http://github.com/jpaoletti/jPOS-Presentation-Manager
 * 
 * */
public class MonitorObserver implements Observer{
    private Monitor monitor;
    private List<String> lines;
    
    /**
     *
     * @param monitor
     */
    public MonitorObserver(Monitor monitor) {
        super();
        this.setMonitor(monitor);
        monitor.addObserver(this);
        setLines(new ArrayList<String>());
    }
    
    /**
     * Inherited from Observer
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        if(arg instanceof String)
            lines.add((String) arg);
        if(arg instanceof List<?>)
            lines.addAll((List<String>) arg);
        if(arg instanceof Exception){
            Exception e = (Exception)arg;
            PresentationManager.pm.error(e);
            lines.add(e.getMessage());
        }
    }

    /**
     * Setter for monitor
     * @param monitor
     */
    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    /**
     * Getter for monitor
     * @return The monitor
     */
    public Monitor getMonitor() {
        return monitor;
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    /**
     * @return the lines and clear them for next time
     */
    public List<String> getLines() {
        List<String> res = new ArrayList<String>();
        res.addAll(lines);
        lines.clear();
        return res;
    }

}

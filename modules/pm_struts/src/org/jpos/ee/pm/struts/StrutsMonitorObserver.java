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
package org.jpos.ee.pm.struts;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.jpos.ee.pm.core.monitor.Monitor;
import org.jpos.ee.pm.core.monitor.MonitorObserver;

/** This class is an observer of the monitor specific for Web implementations.
 * As a Web interface cannot be called, then we don't know when to stop observing.
 * So this observer implements a timer. If the getLines method is not called since
 * 3 times the delay monitor parameter, we asume that this observer is no longer 
 * needed and delete from observers list.
 * 
 * @author jpaoletti jeronimo.paoletti@gmail.com
 * http://github.com/jpaoletti/jPOS-Presentation-Manager
 * 
 * */
public class StrutsMonitorObserver extends MonitorObserver {
    private Timer timer;

    /**
     * Constructor
     *
     * @param monitor The monitor to observe
     */
    public StrutsMonitorObserver(Monitor monitor) {
        super(monitor);
        schedule();
    }

    private void schedule() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                getMonitor().deleteObserver(self());
            }
        };
        timer.schedule(task, getMonitor().getDelay()*3);
    }

    private StrutsMonitorObserver self(){
        return this;
    }

    @Override
    public synchronized List<String> getLines() {
        timer.cancel();
        timer.purge();
        schedule();
        List<String> lines = super.getLines();
        return lines;
    }    
}
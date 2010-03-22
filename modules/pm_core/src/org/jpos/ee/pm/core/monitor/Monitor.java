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

import org.jpos.ee.pm.core.PMCoreObject;

/** A monitor that watch something showing his status.
 * 
 * @author jpaoletti jeronimo.paoletti@gmail.com
 * @see http://github.com/jpaoletti/jPOS-Presentation-Manager
 * 
 * */
public class Monitor extends PMCoreObject{
	/**The id of the monitor. Must be unique*/
	private String id;
	
	/**The source of the monitor information
	 * @see MonitorSource*/
	private MonitorSource source;
	
	/**A formatter for each line generated by monitor.
	 * @see MonitorFormatter */
	private MonitorFormatter formatter;
	
	/**Delay between monitor refreshes in milliseconds*/
	private Integer delay;
	
	/**Maximum number of lines displayed at a time*/
	private Integer max;
	
	/**Clean up after each refresh*/
	private Boolean cleanup;
	
	/**Ignore actual and always get everything*/
	private Boolean all;
	
	public MonitorWatcher newWatcher(){
		return new MonitorWatcher(this);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the source
	 */
	public MonitorSource getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(MonitorSource source) {
		this.source = source;
	}

	/**
	 * @return the formatter
	 */
	public MonitorFormatter getFormatter() {
		return formatter;
	}

	/**
	 * @param formatter the formatter to set
	 */
	public void setFormatter(MonitorFormatter formatter) {
		this.formatter = formatter;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	/**
	 * @return the delay
	 */
	public Integer getDelay() {
		if(delay==null) return 5000;
		return delay;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(Integer max) {
		this.max = max;
	}

	/**
	 * @return the max
	 */
	public Integer getMax() {
		if(max==null) return 100;
		return max;
	}

	/**
	 * @param cleanup the cleanup to set
	 */
	public void setCleanup(Boolean cleanup) {
		this.cleanup = cleanup;
	}

	/**
	 * @return the cleanup
	 */
	public Boolean getCleanup() {
		if(cleanup==null) return false;
		return cleanup;
	}

	/**
	 * @param all the all to set
	 */
	public void setAll(Boolean all) {
		this.all = all;
	}

	/**
	 * @return the all
	 */
	public Boolean getAll() {
		if(all == null) return false;
		return all;
	}
}

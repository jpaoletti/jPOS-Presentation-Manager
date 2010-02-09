/*
 * jPOS Presentation Manager [http://jpospm.blogspot.com]
 * Copyright (C) 2010 Jeronimo Paoletti [jeronimo.paoletti@gmail.com]
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
import java.io.FileReader;
import java.util.Properties;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.JDomDriver;

public class ParameterGetterXML extends ParameterGetterSupport {
	/**A properties object to get some extra configurations*/
	private Properties properties;
	
	public ParameterGetterXML (){
		parse();
	}

	public String getParameter(String id) {
		if(getProperties()==null) return null;
		return getProperties().getProperty(id);
	}
	
	private void parse(){
		XStream xstream;
		xstream = new XStream(new JDomDriver());
		xstream.alias("properties", Properties.class);
		try {
			properties = (Properties) xstream.fromXML (new FileReader ("cfg/pm.paramters.xml"));
		} catch (FileNotFoundException e) {
			properties = null;
			PMLogger.error(e);
		}
	}

	protected Properties getProperties() {
		return properties;
	}
	
}

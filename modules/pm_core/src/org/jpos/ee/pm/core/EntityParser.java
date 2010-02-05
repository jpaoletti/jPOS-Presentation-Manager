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
import java.io.FileReader;

import org.jpos.ee.pm.converter.Converter;
import org.jpos.ee.pm.converter.Converters;
import org.jpos.ee.pm.core.report.Report;
import org.jpos.ee.pm.core.report.ReportFilter;
import org.jpos.ee.pm.validator.Validator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.JDomDriver;

/**This class is the general parser for the PM configurations files.
 * @author yero
 * */
public class EntityParser {
	/**The parser*/
	private XStream xstream;
	
	/**Default constructor*/
	public EntityParser() {
		super();
        xstream = new XStream(new JDomDriver());
        xstream.alias ("entity", Entity.class);
        xstream.alias ("field", Field.class);
        //xstream.alias ("relation", Relation.class);
        xstream.alias ("field-validator", Validator.class);
        xstream.alias ("validator", Validator.class);
        xstream.alias ("operations", Operations.class);
        xstream.alias ("operation", Operation.class);
        xstream.alias ("owner", EntityOwner.class);
        xstream.alias ("report", Report.class);
        xstream.alias ("report-filter", ReportFilter.class);
        xstream.alias ("converters", Converters.class);
        xstream.alias ("converter", Converter.class);
        
        xstream.addImplicitCollection(Entity.class, "fields", Field.class);
        xstream.addImplicitCollection(Converters.class, "converters", Converter.class);
        //xstream.addImplicitCollection(Entity.class, "relations", Relation.class);
        xstream.addImplicitCollection(Field.class, "validators", Validator.class);
        
        xstream.addImplicitCollection(Operations.class, "operations", Operation.class);
        xstream.addImplicitCollection(Operation.class, "validators", Validator.class);
        
        xstream.addImplicitCollection(Report.class,"filters",ReportFilter.class);
        xstream.addImplicitCollection(Report.class,"agroupations",String.class);
        xstream.addImplicitCollection(Report.class,"totalizations",String.class);
        
	}
	
	/**Parse an entity configuration file
	 * @param filename The file name
	 * @return The obtained entity*/
	public Entity parseEntityFile(String filename) throws FileNotFoundException{
		return (Entity) xstream.fromXML (new FileReader (filename));
	}
	
	/**Parse an operations file
	 * @param filename The file name
	 * @return The operations*/
	public Operations parseOperationsFile(String filename) throws FileNotFoundException{
		return (Operations) xstream.fromXML (new FileReader (filename));
	}
	
	
	/**Parse a report file
	 * @param filename The file name
	 * @return The Report*/
	public Report parseReportFile(String filename) throws FileNotFoundException{
		return (Report) xstream.fromXML (new FileReader (filename));
	}

	/**
	 * @return the parser
	 */
	public XStream getXstream() {
		return xstream;
	}
	
}

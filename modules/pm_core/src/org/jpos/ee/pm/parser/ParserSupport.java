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
package org.jpos.ee.pm.parser;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.JDomDriver;
import java.io.FileReader;

/**
 *
 * @author jpaoletti
 */
public abstract class ParserSupport implements PMParser{
    private XStream xstream;

    protected void init(){
        xstream = new XStream(new JDomDriver());
    }
    public Object parseFile(String filename) throws Exception {
        init();
        return xstream.fromXML(new FileReader(filename), newObject());
    }

    public void saveToFile(Object object, String filename) throws Exception{
        init();
        //todo finish
        xstream.toXML(object);
    }

    public XStream getXstream() {
        return xstream;
    }

    protected abstract Object newObject();

}

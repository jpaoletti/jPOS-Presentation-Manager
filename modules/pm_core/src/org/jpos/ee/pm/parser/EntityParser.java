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

import org.jpos.ee.pm.converter.*;
import org.jpos.ee.pm.core.*;
import org.jpos.ee.pm.validator.Validator;

/**
 *
 * @author jpaoletti
 */
public class EntityParser extends ParserSupport {

    @Override
    protected void init() {
        super.init();
        getXstream().alias("entity", Entity.class);

        getXstream().aliasAttribute("no-count", "noCount");

        getXstream().useAttributeFor(Entity.class, "id");
        getXstream().useAttributeFor(Entity.class, "noCount");
        getXstream().useAttributeFor(Entity.class, "clazz");
        getXstream().useAttributeFor(Entity.class, "extendz");

        getXstream().alias("field", Field.class);

        getXstream().useAttributeFor(Field.class, "id");
        getXstream().useAttributeFor(Field.class, "display");
        getXstream().useAttributeFor(Field.class, "align");
        getXstream().useAttributeFor(Field.class, "width");
        getXstream().useAttributeFor(Field.class, "property");

        getXstream().alias("field-validator", Validator.class);
        getXstream().alias("validator", Validator.class);
        getXstream().alias("operations", Operations.class);
        getXstream().alias("operation", Operation.class);

        getXstream().useAttributeFor(Operation.class, "id");
        getXstream().useAttributeFor(Operation.class, "enabled");
        getXstream().useAttributeFor(Operation.class, "scope");
        getXstream().useAttributeFor(Operation.class, "display");
        getXstream().useAttributeFor(Operation.class, "confirm");

        getXstream().alias("owner", EntityOwner.class);
        getXstream().alias("highlights", Highlights.class);
        getXstream().alias("highlight", Highlight.class);

        getXstream().useAttributeFor(PMCoreObject.class, "debug");

        getXstream().useAttributeFor(Highlight.class, "field");
        getXstream().useAttributeFor(Highlight.class, "color");
        getXstream().useAttributeFor(Highlight.class, "value");
        getXstream().useAttributeFor(Highlight.class, "scope");

        getXstream().addImplicitCollection(Entity.class, "fields", Field.class);
        getXstream().addImplicitCollection(Field.class, "validators", Validator.class);

        getXstream().addImplicitCollection(Operations.class, "operations", Operation.class);
        getXstream().addImplicitCollection(Highlights.class, "highlights", Highlight.class);
        getXstream().addImplicitCollection(Operation.class, "validators", Validator.class);

        getXstream().alias("converter", Converter.class);
        getXstream().useAttributeFor(Converter.class, "operations");
        getXstream().useAttributeFor(Converter.class, "validate");

        getXstream().alias("econverter", ExternalConverter.class);
        getXstream().useAttributeFor(ExternalConverter.class, "id");
        getXstream().useAttributeFor(ExternalConverter.class, "operations");

        getXstream().registerConverter(new ConverterConverter());
    }

    @Override
    protected Object newObject() {
        return new Entity();
    }
}

<!--/*
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
 */-->
<%@page import="org.jpos.ee.Constants" import="java.util.Collection"%>
<%@page import="org.jpos.ee.pm.converter.Converter" import="org.jpos.ee.pm.core.Entity"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<bean:define id="tmp_object" name = "entity_instance" type="java.lang.Object"/>
<bean:define id="es" 	 			name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<%  
	Entity weak = es.getPmservice().getEntity(request.getParameter("weakid"));
	Collection listv = (Collection)es.get(tmp_object, request.getParameter("f"));
	request.setAttribute("weak", weak);
	request.setAttribute("operation", weak.getOperations().getOperation("list"));
	request.setAttribute("contents", listv);
%>
<bean:define id="fields" 	  name="weak" property="listableFields" type="java.util.List" toScope="request"/>
<div class="boxed">
	<table id="list" class="display" >
		<thead>
			<tr>
			<logic:iterate id="field" name="fields" type="org.jpos.ee.pm.core.Field">
				<th scope="col" style="width:${field.width}px;" ><pm:field-name entity="${weak}" field="${field}" /></th>
			</logic:iterate>
			</tr>
		</thead>
		<tbody id="list_body" >
			<logic:iterate id="item" name="contents" >
			<tr>
				<logic:iterate id="field" name="fields" type="org.jpos.ee.pm.core.Field" indexId="j">
					<td align="text-align:${field.align};">
						<pm:converted-item operation="${operation}" entity="${weak}" item="${item}" field="${field}" />
					</td>
				</logic:iterate>
			</tr>
			</logic:iterate>
		</tbody>
	</table>
</div>
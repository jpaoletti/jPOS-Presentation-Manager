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
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ page import="org.jpos.ee.pm.struts.PMEntitySupport"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<bean:define id="es" 	 			name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<bean:define id="item_operations" 	name="item_operations" type="org.jpos.ee.pm.core.Operations" />
<% es.putEntityInRequest(request);%>
<logic:iterate id="operation" indexId="i" name="item_operations" property="operations">
	<pm:confirmation operation="${operation}" entity="${entity}" />
	<logic:present name="operation" property="url">
		<bean:define id="furl" value="${operation.url}" />
	</logic:present>
	<logic:notPresent name="operation" property="url">
		<bean:define id="furl" value="${es.context_path}/${operation.id}.do?pmid=${entity.id}&item=${param.i}" />
	</logic:notPresent>
	<a href="${furl}" id="operation${operation.id}" ${onclick}><img src="${es.context_path}/templates/${es.pmservice.template}/img/${operation.id}.gif" alt="${operation.id}" /></a>
</logic:iterate> &nbsp;
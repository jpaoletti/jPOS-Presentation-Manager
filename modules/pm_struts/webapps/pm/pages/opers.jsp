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
<%@ page import="org.jpos.ee.pm.struts.PMEntitySupport"%>
<bean:define id="es" 	 			name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<bean:define id="item_operations" 	name="item_operations" type="org.jpos.ee.pm.core.Operations" />
<% es.putEntityInRequest(request);%>
<logic:iterate id="op" indexId="i" name="item_operations" property="operations">
	<logic:present name="op" property="url">
		<bean:define id="furl" value="${op.url}" />
	</logic:present>
	<logic:notPresent name="op" property="url">
		<bean:define id="furl" value="${es.context_path}/${op.id}.do?pmid=${entity.id}&item=${param.i}" />
	</logic:notPresent>
	<a href="${furl}"><img src="${es.context_path}/templates/${es.pmservice.template}/img/${op.id}.gif" alt="${op.id}" /></a>&nbsp;
</logic:iterate>
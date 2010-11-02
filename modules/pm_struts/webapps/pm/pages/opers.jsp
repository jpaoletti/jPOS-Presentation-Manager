<%--
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
--%>
<%@include file="../inc/inc-full.jsp" %>

<bean:define id="item_operations" 	name="item_operations" type="org.jpos.ee.pm.core.Operations" />
<logic:iterate id="operation" indexId="i" name="item_operations" property="operations" type="org.jpos.ee.pm.core.Operation">
	<pm:confirmation operation="${operation}" entity="${entity}" />
	<logic:present name="operation" property="url">
		<bean:define id="furl" value="${operation.url}" />
	</logic:present>
	<logic:notPresent name="operation" property="url">
		<bean:define id="furl" value="${es.context_path}/${operation.id}.do?pmid=${entity.id}&item=${param.i}" />
	</logic:notPresent>
	<a href="${furl}" id="operation${operation.id}" title="<%=messages.getMessage("operation."+operation.getId())%>" ${onclick}><img src="${es.context_path}/templates/${pm.template}/img/${operation.id}.gif" alt="${operation.id}" /></a>
</logic:iterate> &nbsp;
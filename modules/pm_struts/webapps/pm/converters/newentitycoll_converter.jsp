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
 *--%>
 <%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@page import="org.jpos.ee.pm.core.*" import="org.jpos.ee.Constants" import="java.util.Collection"%>
<%@page import="java.util.List" import="org.jpos.ee.pm.struts.PMEntitySupport" import="org.jpos.ee.pmee.PMList" %>
<bean:define id="tmp_object" name = "entity_instance" type="java.lang.Object"/>
<bean:define id="entity"   	name="entity" 	type="org.jpos.ee.pm.core.Entity" />
<bean:define id="es" 	 	name="es" 		type="org.jpos.ee.pm.struts.PMEntitySupport"  />
<%
	Entity e = es.getPmservice().getEntity(request.getParameter("entity"));
	Collection listv = (Collection)es.get(tmp_object, request.getParameter("f"));
	//TODO Finish this. 
%>
<div id="">
</div>
<logic:iterate id="o" name="list" property="contents" type="java.lang.Object" indexId="i">
	<bean:define id="checked" value="<%= (listv.contains(o))?"checked":"" %>" />
	<input type="checkbox" ${checked} value="${param.entity}@${i}" id="f_${param.f}" name="f_${param.f}" />&nbsp;${o}<br/>
</logic:iterate>
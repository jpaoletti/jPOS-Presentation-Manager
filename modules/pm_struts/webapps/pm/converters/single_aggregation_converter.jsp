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
<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@page import="org.jpos.ee.pm.core.*" import="org.jpos.ee.Constants" import="java.util.Collection"%>
<%@page import="java.util.List" import="org.jpos.ee.pm.struts.PMEntitySupport"  import="org.jpos.ee.pm.struts.PMList"%>
<bean:define id="tmp_object" name = "entity_instance" type="java.lang.Object"/>
<bean:define id="entity"   	name="entity" 	type="org.jpos.ee.pm.core.Entity" />
<bean:define id="es" 	 	name="es" 		type="org.jpos.ee.pm.struts.PMEntitySupport"  />
<%
	Entity e = es.getPmservice().getEntity(request.getParameter("entity"));
	List<?> list = e.getList((PMContext)request.getAttribute(Constants.PM_CONTEXT));
	request.setAttribute("collection", list);
	Object selected = (Object)es.get(tmp_object, request.getParameter("f"));
%>
<select size="1" id="f_${param.f}" name="f_${param.f}">
<c:if test="${param.with_null}">
	<option value="-1" />&nbsp;<br/>
</c:if>
<logic:iterate id="o" name="collection" type="java.lang.Object" indexId="i">
	<bean:define id="checked" value="<%= (o.equals(selected))?"selected":"" %>" />
	<option ${checked} value="${i}" />&nbsp;${o}<br/>
</logic:iterate>
</select>
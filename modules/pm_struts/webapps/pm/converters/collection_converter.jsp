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
<%@include file="../inc/tag-libs.jsp" %>
<%@page import="org.jpos.ee.pm.struts.converter.*"%>
<%@page import="org.jpos.ee.pm.core.*" import="org.jpos.ee.Constants" import="java.util.Collection" import="org.jpos.ee.pm.struts.*"%>
<%@page import="java.util.List" import="org.jpos.ee.pm.struts.PMEntitySupport" %>
<bean:define id="tmp_object" name = "entity_instance" type="java.lang.Object"/>
<bean:define id="entity"     name="entity" type="org.jpos.ee.pm.core.Entity" />
<bean:define id="es"         name="es" 	   type="org.jpos.ee.pm.struts.PMEntitySupport"  />
<%
    PMStrutsContext ctx = (PMStrutsContext)request.getAttribute(Constants.PM_CONTEXT);
    List<?> list = AbstractCollectionConverter.recoverList(ctx, request.getParameter("entity"), false);
	request.setAttribute("collection", list);
	Collection listv = (Collection)ctx.getPresentationManager().get(tmp_object, request.getParameter("prop"));
%>
<logic:iterate id="o" name="collection" type="java.lang.Object" indexId="i">
	<bean:define id="checked" value="<%= (listv!=null && listv.contains(o))?"checked":"" %>" />
	<input type="checkbox" ${checked} value="${i}" id="f_${param.f}" name="f_${param.f}" />&nbsp;${o}<br/>
</logic:iterate>
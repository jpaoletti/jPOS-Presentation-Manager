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
<%@page import="org.jpos.ee.pm.core.*" import="org.jpos.ee.Constants" import="java.util.Collection"%>
<%@page import="java.util.List" import="org.jpos.ee.pm.struts.PMEntitySupport" import="org.jpos.ee.pmee.PMList" %>
<bean:define id="listv"      name="ctx"    property="map.PM_FIELD_VALUE" type="java.util.Collection" />
<bean:define id="collection" name="ctx"    property="tmpList" type="java.util.List" />
<div id="">
</div>
<logic:iterate id="o" name="list" property="contents" type="java.lang.Object" indexId="i">
	<bean:define id="checked" value="<%= (listv.contains(o))?"checked":"" %>" />
	<input type="checkbox" ${checked} value="${param.entity}@${i}" id="f_${param.f}" name="f_${param.f}" />&nbsp;${o}<br/>
</logic:iterate>
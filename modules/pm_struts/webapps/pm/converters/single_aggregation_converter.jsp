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
<%@page import="java.util.List" import="org.jpos.ee.pm.struts.PMEntitySupport" import="org.jpos.ee.pm.struts.converter.*"%>
<%@page import="org.jpos.ee.pm.struts.converter.*" import="org.jpos.ee.pm.struts.*"%>
<bean:define id="collection" name="ctx"    property="tmpList" type="java.util.List" />
<select size="1" id="f_${param.f}" name="f_${param.f}">
<c:if test="${param.with_null}">
	<option value="-1" />&nbsp;<br/>
</c:if>
<logic:iterate id="o" name="collection" type="java.lang.Object" indexId="i">
	<bean:define id="checked" value="${ o eq ctx.map.PM_FIELD_VALUE ? 'selected' : ''}" />
	<option ${checked} value="${i}" />&nbsp;${o}<br/>
</logic:iterate>
</select>
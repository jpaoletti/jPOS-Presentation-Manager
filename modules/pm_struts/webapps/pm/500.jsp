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
<%@include file="inc/tag-libs.jsp" %>
<%@ page isErrorPage="true"  %>
<%@page import="java.io.PrintWriter" import="org.jpos.ee.pm.core.*" import="java.io.StringWriter" %>

<div class="leftpane" align="center">
	<p align="left">
	 <i><bean:message key="errors.500" /></i>
	</p>
	<p align="right">
	 <b><bean:message key="webmaster" /></b>
	</p><br/>
	<%-- Exception Handler --%>
	<font color="red">
	${exception}<br>
	</font>
	<style>
		.exception{
			font-size: small;
			text-align: left;
		}
	</style>
	<xmp class="exception">
	<% 
	if(exception!=null){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		out.print(sw);
		sw.close();
		pw.close();
		if(PresentationManager.pm!=null) PresentationManager.pm.error(exception);
	}
	%>
	</xmp>
	</div>
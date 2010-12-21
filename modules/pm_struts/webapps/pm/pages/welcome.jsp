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
<pm:page title="titles.welcome">
	<div class="boxed">
	<h2 class="title"><bean:message key="index.welcome" arg0="${pmsession.user.name}"/></h2>
	<jsp:useBean id="date" class="java.util.Date"/>
	<p><bean:message key="index.time" /><fmt:formatDate value="${date}" pattern="dd/MM/yyyy HH:mm"/></p>
	</div>
</pm:page>
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
<%@tag description="Navigation bar" pageEncoding="UTF-8"%>
<%@tag import="org.jpos.ee.pm.core.*"%>
<%@attribute name="container" required="false" type="org.jpos.ee.pm.struts.EntityContainer" %>
<%@taglib uri="/WEB-INF/tld/c.tld" prefix="c" %><%@taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<c:if test="${not empty container}">
    <pm:navigation container="${container.owner}" />
    &nbsp; &gt; &nbsp;<a href="${es.context_path}/${container.operation.id}.do?pmid=${container.entity.id}" >${container.selected.instance}</a>
</c:if>
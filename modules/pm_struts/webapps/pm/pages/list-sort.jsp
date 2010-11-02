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
<div id="sort_page" class="jqmWindow">
    <bean:message key='list.sort.field' /> <br/>
    <html:select property="order" onchange="this.form.submit();" value="${PMLIST.order}">
        <logic:iterate id="field" name="entity" property="orderedFields" type="org.jpos.ee.pm.core.Field">
            <c:if test="${fn:contains(field.display,'sort') or fn:contains(field.display,'all')}">
                <html:option value="${field.id}"><pm:field-name entity='${entity}' field='${field}' /></html:option>
            </c:if>
        </logic:iterate>
    </html:select>
    <html:select property="desc" onchange="this.form.submit();" value="${PMLIST.desc}">
        <html:option value="true"><pm:message key="list.sort.desc" /></html:option>
        <html:option value="false"><pm:message key="list.sort.asc" /></html:option>
    </html:select><br/>
</div>

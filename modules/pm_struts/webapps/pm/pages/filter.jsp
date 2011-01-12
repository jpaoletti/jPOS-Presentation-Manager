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
<bean:define id="e_container" name="es" property="container" />
<bean:define id="entity_filter" name="es" property="filter" toScope="request" type="org.jpos.ee.pm.core.EntityFilter"/>
<pm:page title="titles.filter">
    <div id="add" class="boxed">
        <pm:pmtitle entity="${entity}" operation="${ctx.operation}"/>
        <html:form action="/${ctx.operation.id}.do?pmid=${pmid}">
            <html:hidden property="finish" value="yes"/>
            <fieldset>
                <pm:operations labels="true" operations="${ctx.map.operations.operations}"/>
                <div id="navigation_bar">
                    <pm:navigation container="${e_container.owner}"  />
                </div>
                <div class="content">
                    <table id="box-table-a">
                        <tbody id="list_body" >
                            <logic:iterate id="field" name="entity" property="orderedFields" type="org.jpos.ee.pm.core.Field">
                                <c:if test="${fn:contains(field.display,ctx.operation.id) or fn:contains(field.display,'all')}">
                                    <tr>
                                        <th scope="row" width="175px"><div><label for="object.${field.id}"><pm:field-name entity="${entity}" field="${field}" /></label></div></th>
                                        <td><pm:filter-operations field_id="${field.id}" filter="${entity_filter}" /></td>
                                        <td><pm:converted-item operation="${ctx.operation}" entity="${entity}" field="${field}" field_value="${entity_filter.filterValues[field.id][0]}" /></td>
                                    </tr>
                                </c:if>
                            </logic:iterate>
                        </tbody>
                        <tfoot>
                            <tr><td colspan="2"><html:errors/>&nbsp;</td></tr>
                        </tfoot>
                    </table>
                    <html:submit styleId="${entity.id}_submit"><pm:message key="pm.struts.form.submit"/></html:submit>
                    <html:reset styleId="${entity.id}_submit"><pm:message key="pm.struts.form.reset" /></html:reset>		</div>
            </fieldset>
        </html:form>
    </div>
</pm:page>
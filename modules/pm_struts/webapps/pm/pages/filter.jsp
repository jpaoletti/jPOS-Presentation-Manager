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
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/tld/fn.tld" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@page import="org.jpos.ee.pm.core.EntityFilter"%>
<%@page import="org.jpos.ee.Constants"%>
<bean:define id="es" name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<%
es.putEntityInRequest(request);
request.setAttribute("e_container", es.getContainer(request));
es.putFilterInRequest(request);
%>
<pm:page title="titles.filter">
    <div id="add" class="boxed">
        <pm:pmtitle entity="${entity}" operation="${operation}"/>
        <html:form action="/${operation.id}.do?pmid=${pmid}">
            <html:hidden property="finish" value="yes"/>
            <fieldset>
                <pm:operations labels="true" />
                <div id="navigation_bar">
                    <pm:navigation container="${e_container.owner}"  />
                </div>
                <div class="content">
                    <table id="box-table-a">
                        <tbody id="list_body" >
                            <logic:iterate id="field" name="entity" property="orderedFields" type="org.jpos.ee.pm.core.Field">
                                <c:if test="${fn:contains(field.display,operation.id) or fn:contains(field.display,'all')}">
                                    <tr>
                                        <th scope="row" width="175px"><div><label for="object.${field.id}"><pm:field-name entity="${entity}" field="${field}" /></label></div></th>
                                        <td>
                                            <div id="f_${field.id}_div">
                                                <pm:filter-operations field_id="${field.id}" filter="${entity_filter}" />
                                                <pm:converted-item operation="${operation}" entity="${entity}" item="${entity_instance}" field="${field}" />
                                            </div>
                                        </td>
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
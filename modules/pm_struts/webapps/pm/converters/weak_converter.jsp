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
<%
    Entity weak = WeakConverter.getEntity(ctx);
    Collection listv = WeakConverter.getCollection(ctx);
    request.setAttribute("weak", weak);
    request.setAttribute("woperation", weak.getOperations().getOperation("list"));
    request.setAttribute("contents", listv);
%>
<bean:define id="fields" 	  name="weak" property="orderedFields" type="java.util.List" toScope="request"/><br/>
<c:if test="${param.showbutton}">
    <a href="${es.context_path}/list.do?pmid=${param.weakid}" class='button edit' > &nbsp;&nbsp; <pm:message key="pm.struts.weak.converter.edit" /></a>
</c:if>
<c:if test="${param.showlist}">
    <div class="boxed">
        <table id="list" class="display" >
            <thead>
                <tr>
                    <logic:iterate id="field" name="fields" type="org.jpos.ee.pm.core.Field">
                        <c:if test="${fn:contains(field.display,'list') or fn:contains(field.display,'all')}">
                            <th scope="col" style="width:${field.width}px;" ><pm:field-name entity="${weak}" field="${field}" /></th>
                        </c:if>
                    </logic:iterate>
                </tr>
            </thead>
            <tbody id="list_body" >
                <logic:iterate id="item" name="contents" >
                    <tr>
                        <logic:iterate id="field" name="fields" type="org.jpos.ee.pm.core.Field" indexId="j">
                            <c:if test="${fn:contains(field.display,'list') or fn:contains(field.display,'all')}">
                                <td align="text-align:${field.align};">
                                    <pm:converted-item operation="${woperation}" entity="${weak}" item="${item}" field="${field}" />
                                </td>
                            </c:if>
                        </logic:iterate>
                    </tr>
                </logic:iterate>
            </tbody>
        </table>
    </div>
</c:if>
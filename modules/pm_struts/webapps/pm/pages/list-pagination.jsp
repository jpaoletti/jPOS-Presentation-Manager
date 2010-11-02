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
<script type="text/javascript">
    function paginate(i){
        $("#listpage").val(i);
        $("#listform").submit();
    }
</script>
<logic:equal value="true" name="PMLIST" property="paginable">
    <html:hidden property="page" value="${PMLIST.page}" styleId="listpage"/>
    <tr>
        <td colspan="100">
            <pm:message key='pm.struts.list.rpp' />
            <html:select property="rowsPerPage" value="${PMLIST.rowsPerPage}" onchange="this.form.submit();">
                <html:option value="5" />
                <html:option value="10"/>
                <html:option value="20"/>
                <html:option value="50"/>
                <html:option value="100"/>
            </html:select> <pm:message key="pm.struts.list.of" />
            <c:if test="${PMLIST.total != null}">${PMLIST.total}</c:if>
            <c:if test="${PMLIST.total == null}">? &nbsp;</c:if>| &nbsp;&nbsp;&nbsp;&nbsp;

            <c:if test="${PMLIST.page > 1}">
                <a href="javascript:paginate('${PMLIST.page-1}')">&laquo; <pm:message key="pm.struts.list.prev"/></a> |
            </c:if>
            <c:if test="${PMLIST.total != null}">
                <logic:greaterThan value="20" name="PMLIST" property="pages">
                    <pm:list-pagination-link i="${1}" />
                    <html:text property="page" value="${PMLIST.page}" styleId="page" size="5" style="width:25px;" /> |
                    <pm:list-pagination-link i="${PMLIST.pages}" />
                </logic:greaterThan>

                <logic:lessEqual value="20" name="PMLIST" property="pages">
                    <logic:iterate id="i" name="PMLIST" property="pageRange" >
                        <pm:list-pagination-link i="${i}" />
                    </logic:iterate>
                </logic:lessEqual>
            </c:if>

            <c:if test="${PMLIST.total == null}">
                <pm:list-pagination-link i="${1}" />
                <html:text property="page" value="${PMLIST.page}" styleId="page" size="5" style="width:25px;" /> |
            </c:if>
            <c:if test="${PMLIST.total == null || PMLIST.page < PMLIST.pages}">
                <a href="javascript:paginate('${PMLIST.page+1}')"><pm:message key="pm.struts.list.next"/> &raquo;</a>
            </c:if>

        </td>
    </tr>
</logic:equal>
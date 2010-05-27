<%--
 * jPOS Presentation Manager [http://jpospm.blogspot.com]
 * Copyright (C) 2010 Jeronimo Paoletti [jeronimo.paoletti@gmail.com]
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
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@page import="org.jpos.ee.pm.core.*"%>
<bean:define id="pmlist" name="PMLIST" type="org.jpos.ee.pm.core.PaginatedList" />
<bean:define id="has_selected" name="PMLIST" property="hasSelectedScope" type="java.lang.Boolean" />
<bean:define id="es" name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<%
es.putEntityInRequest(request);
int c =  (pmlist.getTotal()==null || pmlist.getTotal() ==0) ? 1 : (int)Math.log10(pmlist.getTotal()) + 1;
%>
<bean:define id="entity"	  name="entity" type="org.jpos.ee.pm.core.Entity" toScope="request"/>
<script type="text/javascript">
function selectItem(i){
    $.ajax({ url: "selectItem.do?pmid="+"${pmid}"+"&idx="+i});
}
</script>
<table id="list" class="display" >
        <thead>
            <tr>
            <th scope="col" style="width:${pmlist.operationColWidth}">&nbsp;</th>
            <logic:iterate id="field" name="fields" type="org.jpos.ee.pm.core.Field">
                <bean:define id="w" value="<%=(field.getWidth().compareTo("")!=0)?"style='width:"+field.getWidth()+"px;'":"" %>"></bean:define>
                <th scope="col" ${w} ><pm:field-name entity="${entity}" field="${field}" /></th>
            </logic:iterate>
            </tr>
        </thead>
        <tbody id="list_body" >
            <bean:define id="contents" name="contents" type="org.jpos.util.DisplacedList" />
            <logic:iterate id="item" name="contents">
            <%
            Integer i = contents.indexOf(item);
            request.setAttribute("i",i);
            Highlight h = entity.getHighlight(null,item);
            if(h!=null) request.setAttribute("pm_hl_class","pm_hl_"+entity.getHighlights().indexOf(h));
            %>
            <tr class="${pm_hl_class}">
                <td style="color:gray; white-space: nowrap;">
                    <logic:equal name="has_selected" value="true">
                        <bean:define id="checked" value="<%=(es.getContainer(request).getSelectedIndexes().contains(i))?"checked":"" %>" />
                        <input type="checkbox" id="selected_item" value="${i}" onchange="selectItem(this.value);" ${checked} />
                    </logic:equal>
                    <%= (pmlist.isShowRowNumber())?String.format("[%0"+c+"d]", i):"" %> &nbsp;
                    <span style="white-space: nowrap;" class="operationspopup" id="g_${i}">
                        <img src="${es.context_path}/templates/${es.pmservice.template}/images/loading.gif" alt="loading" />
                    </span>
                    <script type="text/javascript">
                    $('#g_'+"${i}").load('opers.do?pmid='+"${pmid}"+'&i='+"${i}",
                       function() {
                        //this function fix IE whitespace visualization bug
                        $('#g_'+"${i}").html($('#g_'+"${i}").html().trim());
                       });
                    </script>
                </td>
                <logic:iterate id="field" name="fields" type="org.jpos.ee.pm.core.Field" indexId="j">
                        <td align="${field.align}">
                            <pm:converted-item operation="${operation}" entity="${entity}" item="${item}" field="${field}" />
                    </td>
                </logic:iterate>
            </tr>
            </logic:iterate>
        </tbody>
        <tfoot>
            <logic:equal name="PMLIST" property="searchable" value="true" >
            <tr>
               <th><input type="hidden" name="search" class="search_init" /></th>
               <logic:iterate id="field" name="fields" type="org.jpos.ee.pm.core.Field">
                   <th><input type="text" name="search_<pm:field-name entity="${entity}" field="${field}" />" value="<bean:message key="list.input.search"/><pm:field-name entity="${entity}" field="${field}" />" class="search_init" /></th>
               </logic:iterate>
            </tr>
            </logic:equal>
        </tfoot>
    </table>
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
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/tld/fn.tld" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@page import="org.jpos.ee.pm.core.Entity"%>
<bean:define id="es" 	 			name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<%  es.putEntityInRequest(request);
	es.putItemInRequest(request);
	Entity e = (Entity)request.getAttribute("entity");
	request.setAttribute("operation", e.getOperations().getOperation("show")); 
%>
<pm:page title="titles.add">
	<html:errors />
	<div id="add" class="boxed">
		<pm:pmtitle entity="${entity}" operation="${operation}"/>
		<pm:operations labels="true" />
		<div class="content">
			<table id="box-table-a">
				<tbody id="list_body" >
					<logic:iterate id="field" name="entity" property="fields" type="org.jpos.ee.pm.core.Field">
					<c:if test="${fn:contains(field.display,'show') or fn:contains(field.display,'all')}">
						<tr>
							<th scope="row" width="175px"><pm:field-name entity="${entity}" field="${field}" /></th>
							<td><pm:converted-item operation="${operation}" entity="${entity}" item="${entity_instance}" field="${field}" /></td>
						</tr>
					</c:if>
					</logic:iterate>
				</tbody>
				<tfoot>
					<tr><td colspan="2">&nbsp;</td></tr>
				</tfoot>
			</table>
		</div>
	</div>
	<logic:present name="entity" property="highlights">
	<style>
	<logic:iterate id="highlight" name="entity" property="highlights.highlights">
		.${highlight.field}_${highlight.value} { background-color: ${highlight.color}; }
	</logic:iterate>
	</style>
	</logic:present>	
	<script type="text/javascript" src="${es.context_path}/js/highlight.js"></script>
</pm:page>
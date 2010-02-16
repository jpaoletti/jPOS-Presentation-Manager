<!--/*
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
 */-->
 <%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<table id="list" class="display" >
		<thead>
			<tr>
			<logic:iterate id="field" name="fields" type="org.jpos.ee.pm.core.Field">
				<th scope="col" style="width:${field.width}px;" ><pm:field-name entity="${entity}" field="${field}" /></th>
			</logic:iterate>
			</tr>
		</thead>
		<tbody id="list_body" >
			<logic:iterate id="item" name="contents" indexId="i">
			<tr>
				<logic:iterate id="field" name="fields" type="org.jpos.ee.pm.core.Field" indexId="j">
					<td align="${field.align}">
						<logic:equal name="j" value="0">
							<div class="operations" id="row_${i}"><div class="operationspopup" id="g_${i}">
							<img src="${es.context_path}/templates/${es.pmservice.template}/images/loading.gif" alt="loading" />
							</div>
							<div class="trigger" style="width: ${field.width}px;">
							<pm:converted-item operation="${operation}" entity="${entity}" item="${item}" field="${field}" />
							</div>
							</div>
							<script type="text/javascript">
								var operationdiv = $('#row_'+"${i}");
								var grupodiv = $('#g_'+"${i}");
								grupodiv.load('opers.do?pmid='+"${pmid}"+'&i='+"${i}");
								configurePopup(operationdiv);
							</script>
						</logic:equal>
						<logic:notEqual name="j" value="0">
							<pm:converted-item operation="${operation}" entity="${entity}" item="${item}" field="${field}" />
						</logic:notEqual>
					</td>
				</logic:iterate>
			</tr>
			</logic:iterate>
		</tbody>
		<tfoot>
			
			<logic:equal name="searchable" value="true" >
			<tr>
				<logic:iterate id="field" name="fields" type="org.jpos.ee.pm.core.Field">
					<th><input type="text" name="search_<pm:field-name entity="${entity}" field="${field}" />" value="<bean:message key="list.input.search"/><pm:field-name entity="${entity}" field="${field}" />" class="search_init" /></th>
				</logic:iterate>
			</tr>
			</logic:equal>
		</tfoot>
	</table>
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
	<logic:equal value="true" name="paginable">
			<tr>
			<td colspan="100">
				<bean:message key='list.length.menu' />
				<html:select property="rowsPerPage" value="${PMLIST.rowsPerPage}" onchange="this.form.submit();">
								<html:option value="5" />
								<html:option value="10"/>
								<html:option value="20"/>
				</html:select>			
				<bean:message key='list.total' />&nbsp;${PMLIST.total} &nbsp;
				
				<bean:message key='list.page' />
				<logic:greaterThan value="20" name="PMLIST" property="pages">
					<html:text property="page" value="${PMLIST.page}" styleId="page" size="5" style="width:25px;" />
				</logic:greaterThan>
				<logic:lessEqual value="20" name="PMLIST" property="pages">
					<html:select property="page" value="${PMLIST.page}" onchange="this.form.submit();">
						<logic:iterate id="i" name="PMLIST" property="pageRange" >
							<html:option value="${i}">${i}</html:option>
						</logic:iterate>
					</html:select>
				</logic:lessEqual>
				<bean:message key='list.pages' />&nbsp;${PMLIST.pages}<br/>
			</td>
			</tr>
	</logic:equal>
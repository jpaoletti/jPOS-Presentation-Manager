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
<logic:equal value="true" parameter="checked">
<input type="radio" value="true"  id="f_${param.f}" name="f_${param.f}" checked /><pm:message key="pm.converter.boolean_converter.yes" />
<input type="radio" value="false" id="f_${param.f}" name="f_${param.f}" /> <pm:message key="pm.converter.boolean_converter.no" />
<input type="radio" value="null"  id="f_${param.f}" name="f_${param.f}" /> <pm:message key="pm.converter.boolean_converter.null" />
</logic:equal>
<logic:equal value="false" parameter="checked">
<input type="radio" value="true"  id="f_${param.f}" name="f_${param.f}" /><pm:message key="pm.converter.boolean_converter.yes" />
<input type="radio" value="false" id="f_${param.f}" name="f_${param.f}" checked /><pm:message key="pm.converter.boolean_converter.no" />
<input type="radio" value="null"  id="f_${param.f}" name="f_${param.f}" /> <pm:message key="pm.converter.boolean_converter.null" />
</logic:equal>
<logic:equal value="null" parameter="checked">
<input type="radio" value="true"  id="f_${param.f}" name="f_${param.f}" /><pm:message key="pm.converter.boolean_converter.yes" />
<input type="radio" value="false" id="f_${param.f}" name="f_${param.f}" /><pm:message key="pm.converter.boolean_converter.no" />
<input type="radio" value="null"  id="f_${param.f}" name="f_${param.f}" checked /> <pm:message key="pm.converter.boolean_converter.null" />
</logic:equal>

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
<%@page import="org.jpos.ee.pm.core.*" import="org.jpos.ee.Constants" import="org.jpos.ee.pm.struts.PMEntitySupport" %>
<bean:define id="value" value="${param.value}"/>
<bean:define id="checked" value="${param.isNull ? 'checked' : ''}"/>
<bean:define id="disabled" value="${ (param.isNull and param.withNull) ? 'disabled=disabled' : ''}"/>
<c:if test="${param.withNull}">
    <script type="text/javascript" >
        function disable${param.f}(v){
            if (v){
                $('#f_${param.f}').val("");
                $('#f_${param.f}').attr('disabled', 'disabled');
            }else{
                $('#f_${param.f}').removeAttr('disabled');
            }
        }
    </script>
    <input type="checkbox" ${checked} value="true" id="f_${param.f}_null" name="f_${param.f}_null" onclick="disable${param.f}(this.checked);" />
</c:if>
<input type="text" maxlength="${param.ml}" ${disabled} value="${value}" id="f_${param.f}" name="f_${param.f}" />
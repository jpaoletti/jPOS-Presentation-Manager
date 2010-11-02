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
<%@page import="org.jpos.ee.pm.core.*" import="org.jpos.ee.Constants" %>
<bean:define id="entity"   	name="entity" 	type="org.jpos.ee.pm.core.Entity" />
<input type="password" value="" id="f_${param.f}" name="f_${param.f}" onkeyup="check_equal(this.form);" /><br/>
<input type="password" value="" id="r_${param.f}" name="r_${param.f}" onkeyup="check_equal(this.form);" />
<div id="d_${param.f}" class=""><pm:message key="pm.converter.password_converter.repeat"/></div>
<script type="text/javascript" charset="utf-8">
function check_equal(form){
	var p = form.f_${param.f};
	var r = form.r_${param.f};
	var d = document.getElementById("d_${param.f}");
	var sub = form.${entity.id}_submit
	if(p.value != r.value){
		d.className="red";
		sub.disabled=true;
	}else{
		d.className="";
		sub.disabled=false;
	}
}
</script>
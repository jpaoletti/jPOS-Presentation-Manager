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
<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@page import="org.jpos.ee.pm.core.*" import="org.jpos.ee.Constants" import="org.jpos.ee.pm.struts.PMEntitySupport" %>
<bean:define id="value"  	value="${param.value}"/>
<input type="text" maxlength="${field.maxLength}" value="${value}" id="f_${param.f}" name="f_${param.f}" />
<script type="text/javascript" src="${es.context_path}/js/jquery-ui.js"></script>
<script type='text/javascript'>
$(document).ready(function() {
	$('#f_${param.f}').datepicker({
		buttonImage: '${es.context_path}/templates/${es.pmservice.template}/img/calendar.gif',
		buttonImageOnly: true, 
		buttonText: '', 
		showOn: 'both', 
		speed: 'fast',
		dateFormat: '${param.format}'
	});
});
</script>
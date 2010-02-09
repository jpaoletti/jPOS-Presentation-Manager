<%@tag description="This tag encapsulates site header" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<%@tag import="org.jpos.ee.pm.struts.MenuItemLocationStruts"%><bean:define id="es" 	 			name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<div id="header">
	<pm:topmenu />
	<div id="logo">
		<h1><a href="${es.context_path}"><bean:message key="${es.pmservice.title}"/></a></h1>
		<h2><bean:message key="${es.pmservice.subtitle}"/></h2>
	</div>
	<logic:present name="user">
		<div id="userbox">
		${user.name} &nbsp; |
		&nbsp; <a href="javascript:loadPage('${es.context_path}/show.do?pmid=secuserprofile&identified=id:${user.id}');"><bean:message key="user.profile"/></a> &nbsp; |
		&nbsp; <a href="${es.context_path}/logout.do" title="<bean:message key="logout"/>"><bean:message key="logout"/></a>
		</div> 
	</logic:present>
</div>
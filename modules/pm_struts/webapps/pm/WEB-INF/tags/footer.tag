<%@tag description="" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div id="footer">
	<div id="appversion">v${es.pmservice.appversion}</div>
	<p id="legal">
		<bean:message key="footer.copyright.pre" arg0="2009" />
		<bean:message key='${es.pmservice.title}'/>
		<bean:message key="footer.copyright.post" />
	</p>
</div>
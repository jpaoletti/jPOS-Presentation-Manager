<%@tag description="" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld"  prefix="fmt" %>

<div id="footer">
	<div id="appversion">v${es.pmservice.appversion}</div>
	<p id="legal">
		<bean:message key="footer.copyright.pre" />
		<fmt:formatDate pattern="yyyy"  value="<%=new java.util.Date() %>" />
		<bean:message key='${es.pmservice.title}'/>
		<bean:message key="footer.copyright.post" />
	</p>
</div>
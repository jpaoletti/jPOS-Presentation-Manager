<%@tag description="" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<div id="topmenu">
		<ul>
			<li><html:link href="${es.context_path}/index.jsp" styleId="topmenu1"><bean:message key="home"/></html:link></li>
			<li><a href="${es.pmservice.contact}" id="topmenu3" accesskey="3" title="<bean:message key="contact"/>"><bean:message key="contact"/></a></li>
		</ul>
</div>
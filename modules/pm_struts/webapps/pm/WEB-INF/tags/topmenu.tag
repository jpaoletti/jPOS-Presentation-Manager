<%@tag description="" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<div id="topmenu">
		<ul>
			<li><html:link href="${es.context_path}/index.jsp" styleId="topmenu1"><pm:message key="home"/></html:link></li>
			<li><a href="${pm.contact}" id="topmenu3" accesskey="3" title="<pm:message key="contact"/>"><pm:message key="contact"/></a></li>
		</ul>
</div>
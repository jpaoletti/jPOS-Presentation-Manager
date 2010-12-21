<%@tag description="" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld"  prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<div id="footer">
    <div id="appversion">v${pm.appversion}</div>
    <p id="legal"><pm:message key="footer.copyright.pre" /><fmt:formatDate pattern="yyyy"  value="<%=new java.util.Date() %>" />&nbsp;${pm.copyright}&nbsp;<pm:message key="footer.copyright.post" /></p>
</div>
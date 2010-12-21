<%@tag description="This tag encapsulates site header" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<bean:define id="es" name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<div id="header">
    <pm:topmenu />
    <div id="logo">
        <h1><a href="${es.context_path}"><pm:message key="${pm.title}"/></a></h1>
        <h2><pm:message key="${pm.subtitle}"/></h2>
    </div>
    <c:if test="${not empty pmsession.user}">
    <div id="userbox">
        ${pmsession.user.name} &nbsp; |
        <logic:equal value="true" name="pm" property="loginRequired">
        &nbsp; <a href="javascript:loadPage('${es.context_path}/show.do?pmid=secuserprofile&identified=username:${pmsession.user.username}');"><pm:message key="user.profile"/></a> &nbsp; |
        </logic:equal>
        &nbsp; <a href="${es.context_path}/logout.do" title="<pm:message key="logout"/>"><pm:message key="logout"/></a>
    </div>
    </c:if>
</div>
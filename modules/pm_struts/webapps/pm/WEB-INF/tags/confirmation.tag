<%@tag description="This tag encapsulates a PM title" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@attribute name = "entity" required="true" type="org.jpos.ee.pm.core.Entity"%>
<%@attribute name = "operation" required="true" type="org.jpos.ee.pm.core.Operation"%>
<c:if test="${operation.confirm}">
	<p id="q_${operation.id}" style="display: none;" >
	<bean:define id="messages" name="org.apache.struts.action.MESSAGE" type="org.apache.struts.util.MessageResources" scope="application"/>
	<pm:message key="pm.operation.confirm.question"  
				arg0="<%=messages.getMessage("operation."+operation.getId())%>" 
				arg1="<%=messages.getMessage("pm.entity."+entity.getId())%>" />
	</p>
	<bean:define id="onclick" 	value="onclick=\"return confirm($('#q_${operation.id}').html().trim());\" " toScope="request"/>
</c:if>
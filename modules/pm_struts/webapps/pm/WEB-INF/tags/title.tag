<%@tag description="This tag encapsulates a title" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@attribute name = "key" required="true" type="java.lang.String" %>
<%@attribute name = "key_operation" required="true" type="java.lang.String" %>
<h2 class="title">
		<pm:message key="${key}" />
		&nbsp;
		(<pm:message key="${key_operation}" />)
		<a href="javascript:location.reload(true)">
			<img alt="<bean:message key="list.refresh"/>" 
				 src="${es.context_path}/templates/${pm.template}/img/reload.gif"
				 style="vertical-align:middle;"
			 />
		</a>
</h2>
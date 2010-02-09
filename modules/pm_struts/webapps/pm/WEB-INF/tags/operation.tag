<%@ tag description="This tag builds an Operation view" pageEncoding="UTF-8"%>
<%@ tag import="org.jpos.ee.pm.core.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@ attribute name="operation" required="true" type="org.jpos.ee.pm.core.Operation" %>
<bean:define id="style" value="background-image:url(${es.context_path}/templates/${es.pmservice.template}/img/${operation.id}.gif);" />
<%@attribute name="labels" required="false" type="java.lang.Boolean" description="If this value is set to true, then buttons will have label." %>
<c:if test="${operation.url != null}">
	<a href='${operation.url}' class='button' style="${style}" id="operation${operation.id}">
</c:if>
<c:if test="${operation.url == null}">
	<a href='${es.context_path}/${operation.id}.do?pmid=${entity.id}&item=${param.item}' class='button' style="${style}">
</c:if>
&nbsp;
<c:if test="${labels == null or labels}">
	<pm:message key="operation.${operation.id}" arg0="pm.entity.${entity.id}"/>&nbsp;
</c:if></a>
<%@tag description="This tag encapsulates a PM title" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@attribute name = "entity" required="true" type="org.jpos.ee.pm.core.Entity" required="true" %>
<%@attribute name = "operation" required="true" type="org.jpos.ee.pm.core.Operation" required="true" %>
<logic:equal value="true" name="operation" property="showTitle">
<pm:title key="pm.entity.${entity.id}" key_operation="operation.${operation.id}" />
</logic:equal>
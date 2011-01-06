<%@ tag description="This tag shows the operations" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@attribute name="labels" required="false" type="java.lang.Boolean" %>
<%@attribute name="operations" required="false" type="java.util.List" %>
<% if(operations!=null && operations.size() > 0){ %>
<div id="operation_bar">
<logic:iterate id="operation" name="operations" type="java.lang.Object" >
	<pm:operation operation="${operation}" labels="${labels}" />
</logic:iterate>
</div>
<% } %>
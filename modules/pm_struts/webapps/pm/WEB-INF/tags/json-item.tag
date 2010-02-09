<%@tag description="This tag builds a json item for the value of a field bean" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ attribute name = "item"  required="true" type="java.lang.Object" %>
<%@ attribute name = "field" required="true" type="org.jpos.ee.pm.core.Field" %>
<%@ attribute name = "operation" required="true" type="org.jpos.ee.pm.core.Operation" %>

<%@tag import="org.jpos.ee.pm.converter.Converter"%><bean:define id="entity"   	name="entity" 	type="org.jpos.ee.pm.core.Entity" />
<bean:define id="es" name="es" scope="session" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<%
	Converter c = field.getConverters().getConverterForOperation(operation.getId());
	if(c!= null) out.print(c.visualize(entity,field,operation,item,""));
	else out.print(item);
%>
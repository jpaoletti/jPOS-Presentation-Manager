<%@ tag description="This tag builds the name of a field" pageEncoding="UTF-8"%>
<%@attribute name = "entity"	required="true" type="org.jpos.ee.pm.core.Entity" %>
<%@attribute name = "field"		required="true" type="org.jpos.ee.pm.core.Field" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@tag import="org.jpos.ee.pm.core.PMLogger"%>
<bean:define id="key" value="pm.field.${entity.id}.${field.id}" />
<%try{ %>
<bean:message key="${key}" /> 
<%}catch(Exception e){
	PMLogger.error("Key "+key+" not found");
	out.print(key);
} %>


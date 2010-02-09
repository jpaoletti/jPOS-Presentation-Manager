<%@tag description="This tag builds a list cell" pageEncoding="UTF-8"%>
<%@attribute name = "item" 		required="true" type="java.lang.Object" %>
<%@attribute name = "entity"	required="true" type="org.jpos.ee.pm.core.Entity" %>
<%@attribute name = "field"		required="true" type="org.jpos.ee.pm.core.Field" %>
<%@attribute name = "operation"	required="true" type="org.jpos.ee.pm.core.Operation" %>
<%@tag import="org.jpos.ee.pm.core.PMLogger"%>
<%try{ %>
<div class="cell ${field.id}"><jsp:include page="<%= "../converters/"+field.visualize(entity,operation,item,"") %>" flush="true" /></div>
<%}catch(Exception e){PMLogger.error(e);%>
<img width="16px" src='${es.context_path}/templates/${es.pmservice.template}/images/m_error.png' alt='error' />
<%}%>
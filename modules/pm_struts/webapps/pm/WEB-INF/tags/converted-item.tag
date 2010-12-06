<%@tag description="This tag builds a list cell" pageEncoding="UTF-8"%>
<%@attribute name = "item" 		required="false" type="java.lang.Object" %>
<%@attribute name = "entity"	required="true" type="org.jpos.ee.pm.core.Entity" %>
<%@attribute name = "field"		required="true" type="org.jpos.ee.pm.core.Field" %>
<%@attribute name = "operation"	required="true" type="org.jpos.ee.pm.core.Operation" %>
<%@attribute name = "field_value" required="false" type="java.lang.Object" %>

<%@tag import="org.jpos.ee.pm.core.*"%><%@tag import="org.jpos.ee.Constants"%>
<%
try{
        PMContext ctx = (PMContext)request.getAttribute(Constants.PM_CONTEXT);
        ctx.put(Constants.PM_FIELD,field);
        if(field_value != null){
            ctx.put(Constants.PM_FIELD_VALUE, field_value);
        }else{
            ctx.put(Constants.PM_FIELD_VALUE, ctx.getPresentationManager().get(item, field.getProperty()));
        }
        ctx.put(Constants.PM_ENTITY_INSTANCE,item);
        ctx.put(Constants.PM_EXTRA_DATA,"");
        request.setAttribute("ctx",ctx);
%>
<div class="cell"><jsp:include page="<%= "../converters/"+field.visualize(ctx, operation, entity)+"&f="+field.getId() %>" flush="true" /></div>
<%}catch(Exception e){PresentationManager.pm.error(e);%>
<img width="16px" src='${es.context_path}/templates/${pm.template}/images/m_error.png' alt='error' />
<%}%>
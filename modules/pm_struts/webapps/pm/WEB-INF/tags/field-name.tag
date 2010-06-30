<%@ tag description="This tag builds the name of a field" pageEncoding="UTF-8"%>
<%@attribute name = "entity"	required="true" type="org.jpos.ee.pm.core.Entity" %>
<%@attribute name = "field"		required="true" type="org.jpos.ee.pm.core.Field" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<pm:message key="pm.field.${entity.id}.${field.id}" />

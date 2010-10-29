<%@ tag description="This tag builds a select with filter possible operations" pageEncoding="UTF-8"%>
<%@attribute name = "field_id" required="true"  %>
<%@attribute name = "filter" required="true"  type="org.jpos.ee.pm.core.EntityFilter" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<bean:define id="selected" value="<%= filter.getFilterOperation(field_id).toString() %>" />
<select id="filter_oper_f_${field_id}" name="filter_oper_f_${field_id}" style="float:left;">
    <option value="0" ${selected=='EQ' ? 'selected' : ''} ><pm:message key="pm.struts.filter.eq" /></option>
    <option value="1" ${selected=='NE' ? 'selected' : ''} ><pm:message key="pm.struts.filter.ne" /></option>
    <option value="2" ${selected=='LIKE' ? 'selected' : ''} ><pm:message key="pm.struts.filter.like" /></option>
    <option value="3" ${selected=='GT' ? 'selected' : ''} ><pm:message key="pm.struts.filter.gt" /></option>
    <option value="4" ${selected=='GE' ? 'selected' : ''} ><pm:message key="pm.struts.filter.ge" /></option>
    <option value="5" ${selected=='LT' ? 'selected' : ''} ><pm:message key="pm.struts.filter.lt" /></option>
    <option value="6" ${selected=='LE' ? 'selected' : ''} ><pm:message key="pm.struts.filter.le" /></option>
</select>

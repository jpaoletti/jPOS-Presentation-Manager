<%@tag import="org.jpos.ee.pm.core.PMLogger"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@attribute name = "key" required="true" type="java.lang.String" %>
<%@attribute name = "arg0" required="false" type="java.lang.String" %>
<%try{ %><bean:message key="${key}" arg0="${arg0}" /><%}catch(Exception e){PMLogger.error("Key "+key+" not found"); out.print(key);} %>
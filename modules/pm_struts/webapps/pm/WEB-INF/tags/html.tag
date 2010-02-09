<%-- 
    Document   : html
    Created on : 02/04/2009, 22:22:00
    Author     : jpaoletti
--%>
<%@tag description="This tag encapsulates a standard html header" pageEncoding="UTF-8" import="org.jpos.ee.pm.struts.PMStrutsService" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@attribute name="title" required="false"%>
<%@attribute name="checkUser" required="true" type="java.lang.Boolean"%>

<div id="page-container">
  <pm:header />

<% if(checkUser){ %>
 <logic:notPresent scope="session" name="user">
 	<div id="menu" class="jqueryslidemenu"><ul></ul></div>
 	<div>
 	<iframe frameborder="0"  id="content" src="${es.context_path}/pages/login.jsp" width="75%" height="75%"></iframe>
 </logic:notPresent>
 
 <logic:present scope="session" name="user">
 	<pm:menu />
 	<iframe frameborder="0"  id="content" width="75%" height="75%" src=""></iframe>
 </logic:present>  
<%}else{ %>
 	<pm:menu />
 	<iframe frameborder="0"  id="content" width="75%" height="75%" src=""></iframe>
 %} %> 

	<pm:footer />
</div>

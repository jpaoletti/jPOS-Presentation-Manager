<%-- 
    Document   : page
    Created on : 02/04/2009, 22:22:00
    Author     : jpaoletti
--%>
<%@ tag description="This tag encapsulates a standard html page" pageEncoding="UTF-8" import="org.jpos.ee.pm.struts.PMStrutsService" %>
<%@ tag import="org.jpos.ee.pm.struts.PMEntitySupport"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@attribute name="title" required="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@tag import="org.jpos.ee.pm.core.*"%><html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" /> 
<title><pm:message key="${pm.title}"/> - <pm:message key="${title}"/></title>
<link href="${es.context_path}/templates/${pm.template}/all.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${es.context_path}/js/jquery.js"></script>
<script type="text/javascript" src="${es.context_path}/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${es.context_path}/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="${es.context_path}/js/misc.js"></script>
<!-- 
<link rel="shortcut icon" href="${cp}/styles/images/ico.ico"> 
-->
</head>

<!--[if lte IE 7]>
<style type="text/css">
html .jqueryslidemenu{height: 1%;} /*Holly Hack for IE7 and below*/
</style>
<![endif]-->
<body>
<% try{ %>
	<jsp:doBody />
<% }catch(Exception e){
	PresentationManager.pm.error(e);
	%>
	<pm:message key="pm.page.error"/>
	<%
} %>
</body>
</html>
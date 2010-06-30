<%-- 
    Document   : html
    Created on : 02/04/2009, 22:22:00
    Author     : jpaoletti
--%>
<%@tag description="This tag encapsulates the login form" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<script src="${es.context_path}/js/md5.js" type="text/javascript"></script>
<script src="${es.context_path}/js/cookies.js" type="text/javascript"></script>
<script src="${es.context_path}/js/encrypt.js" type="text/javascript"></script>

<div id="login" class="boxed">
			<h2 class="title"><bean:message key="login"/> </h2>
			<div class="content">
				<html:form action="/login" method="POST"  onsubmit="return encrypt(this.username, this.password);">
					<fieldset>
					<logic:equal value="true" name="pm" property="loginRequired">
						<legend><bean:message key="login.sign.in" /></legend>
						<label for="username"><bean:message key="login.username" /></label>
						<html:text property="username" styleId="username"></html:text>
						<label for="password"><bean:message key="login.password" /></label>
						<html:password property="password" styleId="password" value=""></html:password>
					</logic:equal>
					<logic:equal value="false" name="pm" property="loginRequired">
						<html:hidden property="username" value="xxxxx"/>
						<html:hidden property="password" value="xxxxx"/>
					</logic:equal>
					<html:submit styleId="submit"><bean:message key="login.sign.in" /></html:submit>
					</fieldset>
				</html:form>
			</div>
			<html:errors/>
</div>
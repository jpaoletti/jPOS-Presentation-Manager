<%@tag description="This tag encapsulates user menu" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<div id="menu" class="jqueryslidemenu">
	<ul>
		<logic:present name="menu">
			<logic:iterate id="m" name="menu" property="submenus">
			<pm:submenu menu="${m}" />
			</logic:iterate>
		</logic:present>
	</ul>
</div>
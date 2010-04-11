<%@tag description="" pageEncoding="UTF-8"%>
<%@attribute name="menu" required="true" type="org.jpos.ee.pm.menu.Menu" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@tag import="org.jpos.ee.pm.struts.MenuItemContext"%>
<bean:define id="es" 	 			name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<!-- we need to test if user has permissions -->
<%@tag import="org.jpos.ee.pm.menu.MenuItem"%>
<logic:present name="menu" property="submenus">
    <li><a href="#"><pm:message key="${menu.text}" /></a>
    <ul>
        <logic:iterate id="m" name="menu" property="submenus">
        <pm:submenu menu="${m}" />
        </logic:iterate>
    </ul>
    </li>
</logic:present>
<logic:notPresent name="menu" property="submenus">
	<bean:define id="item" name="menu" type="org.jpos.ee.pm.menu.MenuItem" />
	<li>
		<logic:empty name="item" property="location">
			<a href="#"><pm:message key="${menu.text}" /></a>
		</logic:empty>
		<logic:notEmpty name="item" property="location">
			<% request.setAttribute("context",item.getLocation().build(item,es.getContext_path()));	%>
			${context.prefix}<pm:message key="${context.value}"/>${context.sufix}
		</logic:notEmpty>
	</li>
</logic:notPresent>
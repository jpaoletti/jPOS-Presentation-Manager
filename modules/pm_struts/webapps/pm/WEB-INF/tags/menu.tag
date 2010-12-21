<%@tag description="This tag encapsulates user menu" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<div id="menu" class="jqueryslidemenu">
    <ul>
    <c:if test="${not empty pmsession.menu}">
        <logic:iterate id="m" name="pmsession" property="menu.submenus">
            <pm:submenu menu="${m}" />
        </logic:iterate>
    </c:if>
    </ul>
</div>
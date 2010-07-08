<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/fn.tld" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@page import="org.jpos.ee.pm.core.Entity"%>
<bean:define id="es" name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<script src="${es.context_path}/js/md5.js" type="text/javascript"></script>
<script src="${es.context_path}/js/cookies.js" type="text/javascript"></script>
<script src="${es.context_path}/js/encrypt.js" type="text/javascript"></script>
<%  es.putEntityInRequest(request);
    es.putItemInRequest(request);
    Entity e = (Entity)request.getAttribute("entity");
%>
<pm:page title="titles.add">
    <div id="add" class="boxed">
        <pm:pmtitle entity="${entity}" operation="${operation}" />
        <pm:operations labels="true" />
        <div class="content">
            <form method="post" onsubmit="return encrypt(this.username, this.actual);">
                <fieldset>
                    <input type="hidden" value="1" name="finish" />
                    <input type="hidden" value="${user.username}" name="username">
                    <table id="box-table-a">
                        <tbody id="list_body" >
                            <tr>
                                <th scope="row" width="175px"><bean:message key="chpass.actual" /></th>
                                <td><input type="password" name="actual" id="actual" value=""><br/>
                                    <html:errors property="actual" /></td>
                            </tr>
                            <tr>
                                <th scope="row" width="175px"><bean:message key="chpass.newpass" /></th>
                                <td><input type="password" name="newpass" id="newpass" value=""><br/>
                                    <html:errors property="newpass" /></td>
                            </tr>
                            <tr>
                                <th scope="row" width="175px"><bean:message key="chpass.newrep" /></th>
                                <td><input type="password" name="newrep" id="newrep" value=""><br/>
                                    <html:errors property="newrep" /></td>
                            </tr>
                        </tbody>
                        <tfoot>
                            <tr><td colspan="2"><html:errors /></td></tr>
                        </tfoot>
                    </table>
                    <html:submit styleId="${entity.id}_submit">
                        <pm:message key="pm.security.ui.changepsw.submit" />
                    </html:submit>
                </fieldset>
            </form>
        </div>
    </div>
</pm:page>
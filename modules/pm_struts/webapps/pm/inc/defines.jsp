<%--
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2010 Alejandro P. Revilla
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%--
  General definitions. Requires tag-libs.jsp

  @author jpaoletti
--%>
<bean:define id="ctx" name="PM_CONTEXT" type="org.jpos.ee.pm.struts.PMStrutsContext" toScope="request" />
<bean:define id="es" name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" toScope="request" />
<jsp:setProperty name="es" property="request" value="<%= request %>" />
<logic:present name="ctx" property="entity">
    <bean:define id="entity" name="ctx" property="entity" toScope="request" type="org.jpos.ee.pm.core.Entity" />
</logic:present>
<bean:define id="messages" name="org.apache.struts.action.MESSAGE" type="org.apache.struts.util.MessageResources" toScope="application"/>

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
<%@include file="../inc/tag-libs.jsp" %>
<pm:page title="login">
    <logic:present name="user">
        <script type="text/javascript" charset="utf-8">
            parent.location = "/";
        </script>
    </logic:present>
    <logic:present name="reload" scope="request">
        <script type="text/javascript" charset="utf-8">
            parent.location = "/";
        </script>
    </logic:present>
    <logic:notPresent name="user">
        <pm:login />
    </logic:notPresent>
</pm:page>
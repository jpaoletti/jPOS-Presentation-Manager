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
<%@include file="inc/tag-libs.jsp" %>
<script type="text/javascript" charset="utf-8">
    function loadPage(url){
        window.frames["mainframe"].location = url;
    }
</script>
<logic:present name="pm">
    <pm:page title="titles.index" >
        <div id="page-container">
            <pm:header />
            <%-- <pm:menu /> --%>
            <jsp:include page="pages/menu.jsp" />

            <div id="content">
                <logic:notPresent scope="session" name="user">
                    <iframe id="mainframe" name="mainframe" frameborder="0"  width="100%" height="75%" src="${es.context_path}/pages/login.jsp" >
                    </iframe>
                </logic:notPresent>

                <logic:present scope="session" name="user">
                    <iframe id="mainframe" name="mainframe" frameborder="0"  width="100%" height="75%" src="${es.context_path}/pages/welcome.jsp">
                    </iframe>
                </logic:present>
            </div>

            <pm:footer />
        </div>
    </pm:page>
</logic:present>

<logic:notPresent name="pm">
    <style type="text/css" >
        #pm_error_div{
            margin: 70px;
            padding: 40px;
            border-color: black;
            border-style: solid;
            border-width: 1px;
            background-color: red;
            font-size: large;
            font-weight: bold;
            width:400px;
        }
        #error_img{
            vertical-align:middle;
            float:left;
            width:100px;
            margin: 0px 20px 0px 0px;
        }
    </style>
    <div id="pm_error_div">
        <img alt="error" src="error.png" id="error_img">
        <bean:message key="pm.not.present"/>
    </div>
</logic:notPresent>
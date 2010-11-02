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
<bean:define id="es"                name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<pm:page title="titles.monitor">
    <div class="boxed">
        <pm:title key="pm.monitor.${monitor.id}" key_operation="operation.monitor"/>
    </div>
    <div id="con" class="boxed monitor_window monitor_${monitor.id}">
        <div id='line_container' > </div>
    </div>
    <html:errors />
    <script src="../js/jquery-plugin-arte.js" type="text/javascript"></script>
    <script type="text/javascript">
            $(document).ready(function(){
                $.arte({'ajax_url':'${es.context_path}/monitor.do?pmid=${monitor.id}&continue=true', 'on_success':update_field, 'time':'${monitor.delay}' }).start();
            });
            function update_field(data){
                var cleanup = ${monitor.cleanup};
                if(data.trim().length > 0){
                    var res =  "<pre style='WHITE-SPACE: pre'>";
                    if(!cleanup) res = res+$("#line_container").html();
                    res=res+data+"</pre>";
                    $("#line_container").html(res);
                    $("#con").animate({ scrollTop: $("#con").attr("scrollHeight") - $('#con').height() }, 1000);
                }
            }
    </script>
</pm:page>
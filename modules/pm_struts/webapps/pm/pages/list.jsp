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
<%@include file="../inc/inc-full.jsp" %>
<bean:define id="e_container"   name="ctx" property="entityContainer" toScope="request"/>
<bean:define id="operation"     name="ctx" property="operation" toScope="request"/>
<bean:define id="PMLIST"        name="e_container" property="list" toScope="request"/>
<bean:define id="operations"    name="PMLIST" property="operations" type="org.jpos.ee.pm.core.Operations" toScope="request"/>
<bean:define id="contents" 	name="PMLIST" property="contents" type="java.util.List<Object>" toScope="request"/>
<pm:page title="list">
<script type="text/javascript" src="${es.context_path}/js/jquery.modal.js"></script>
<script type="text/javascript" src="${es.context_path}/js/jquery.center.js"></script>
<div class="boxed">
<html:form method="post" action="/list" styleId="listform">
	<input type="hidden" name="finish" value="1" />
	<pm:pmtitle entity="${entity}" operation="${operation}" />
    <pm:operations labels="true" operations="${operations.operations}"/>
    <div id="navigation_bar">
    <pm:navigation container="${e_container.owner}"  />
    </div>
    <script type="text/javascript" charset="utf-8">
		var pmid = "${entity.id}";
		var searchable = "${PMLIST.searchable}" == "true";
		var sortable = false;
		var paginable = false;
		var texts = new Array(
				"<bean:message key='list.search.all' />" ,
				"<bean:message key='list.first' />" ,
				"<bean:message key='list.last' />" ,
				"<bean:message key='list.next' />" ,
				"<bean:message key='list.previous' />" ,
				"<bean:message key='list.info' />" ,
				"<bean:message key='list.info.empty' />" ,
				"<bean:message key='list.info.filtered' />" ,
				"<bean:message key='pm.struts.list.rpp' />" ,
				"<bean:message key='list.processing' />" ,
				"<bean:message key='list.zero.records' />"
					);
	</script>
	<script type="text/javascript" charset="utf-8" src="${es.context_path}/js/list.js"></script>

	<div id="list-container">
		<div id="example_wrapper" class="dataTables_wrapper">
			<jsp:include page="list-table.jsp" />
			<jsp:include page="list-pagination.jsp" />
			<jsp:include page="list-sort.jsp" />
		</div>
	</div>

	<html:errors/>

	<logic:present name="entity" property="highlights">
        <style type="text/css">
	<logic:iterate id="highlight" name="entity" property="highlights.highlights" indexId="i">
		<logic:equal value="instance" name="highlight" property="scope">
		tr.pm_hl_${i} { background-color: ${highlight.color}; }
		</logic:equal>
		<logic:notEqual value="instance" name="highlight" property="scope">
		td.pm_hl_${i} { background-color: ${highlight.color}; }
		</logic:notEqual>
	</logic:iterate>
	</style>
	</logic:present>

	<script type="text/javascript">
	$(function(){
		var myOpen=function(hash){
			$('#sort_page').center();
			hash.w.css('opacity',0.88).show();
		};

		$("#page").keydown(function(event){
		    if(event.keyCode == 13)
		       this.form.submit();
		});
		$('#operationsort').addClass('jqModal');
		$('#sort_page').jqm({onShow:myOpen});
	});
	</script>
</html:form>
</div>
</pm:page>
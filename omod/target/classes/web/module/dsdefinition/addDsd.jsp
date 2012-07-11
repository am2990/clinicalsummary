<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/localHeader.jsp"%>



<script type="text/javascript" src="/openmrs18/moduleResources/reportingsummary/scripts/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/openmrs18/moduleResources/reportingsummary/scripts/jquery/jquery-ui.min.js"></script>
<!-- 
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/jquery-ui.min.js"></script> -->
<%@ include file="../includes/js_css.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"> 
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/> 
	<script type="text/javascript">
		$(function(){
			$.localise('ui-multiselect', {/*language: 'en',*/ path: 'js/locale/'});
			$(".multiselect").multiselect();
			$('#switcher').themeswitcher();
		});
	</script>
</head>

<script>
function setPage(type){window.location.href=openmrsContextPath+'/module/reportingsummary/summaryrenderer.form?type='+type}
</script>
<!-- <a href="#" onclick="setPage('xml');">Download Summary XML</a>-->
<!-- <a href="#" onclick="setPage('csv');">Download Summary CSV</a>-->

<body>
<h1>Add New DataSetDefinition</h1>
<form method="post" class="box" id="dataElementForm">

	
	
	<td><spring:message code="general.name"/></td>: <input type="text" name="DSDName" value="" /><br />
	</br>
	<td><spring:message code="reportingsummary.DSD.code"/></td>: <input type="text" name="DSDCode" value="" /><br />
	</br>
    
    <select id="datasets" class="multiselect" multiple="multiple" name="datasets[]">
    	<c:forEach items="${patientAttributes}" var="pa">
			<option value="${pa}">${pa}</option>
		</c:forEach>
    </select>
    </br>
    <input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="general.save"/>">
	<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="general.cancel"/>" onclick="ACT.go('viewDsd.form');">
</form>

<input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="general.save"/>">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="general.cancel"/>" onclick="ACT.go('listDataElement.form');">

</body> 

</html>



<%@ include file="/WEB-INF/template/footer.jsp"%>

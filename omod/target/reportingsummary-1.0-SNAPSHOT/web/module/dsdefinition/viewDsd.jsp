<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/localHeader.jsp"%>
<openmrs:require privilege="View DSDefinitions" otherwise="/login.htm" redirect="/module/reportingsummary/viewDefinition.form" />

<spring:message var="pageTitle" code="reportingsummary.DSD.view" scope="page"/>

<h2><spring:message code="reportingsummary.DSD.view"/></h2>	

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code='reportingsummary.DSD.add'/>" onclick="window.open('/openmrs18/module/reportingsummary/dsdefinition/addDsd.form','Add DSD','status=1');"/>

<br /><br />
<input type="hidden" id="pageId" value="listDSDPage" />
<form method="post" onsubmit="return false" id="form">
<table cellpadding="5" cellspacing="0"  >
	<tr>
		<td><spring:message code="general.name"/></td>
		<td><input type="text" id="searchName" name="searchName" value="${searchName}" /></td>
		<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search" onclick="ReportingSummary.search('listReport.form','searchName');"/></td>
	</tr>
</table>
<span class="boxHeader"><spring:message code="reportingsummary.DSD.list"/></span>
<div class="box">
<c:choose>
<c:when test="${not empty dsdefinitions}">
<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick="ReportingSummary.checkValue();" value="<spring:message code='reportingsummary.deleteSelected'/>"/>
<table cellpadding="5" cellspacing="0" width="100%">
<tr>
	<th>#</th>
	<th>No</th>
	<th><spring:message code="general.name"/></th>
	<th><spring:message code="reportingsummary.DSD.code"/></th>
	<th><spring:message code="reportingsummary.DSD.createdOn"/></th>
	<th><spring:message code="reportingsummary.DSD.createdBy"/></th>
	<th>Data Sets</th>
	<th>Download</th>
	<th>View</th>
</tr>
<c:forEach items="${dsdefinitions}" var="dsd" varStatus="varStatus">
	<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><input type="checkbox" name="ids" value="${dsd.id}"/></td>
		<td><c:out value="${(( pagingUtil.currentPage - 1  ) * pagingUtil.pageSize ) + varStatus.count }"/></td>	
		<td><a href="#" onclick="window.open('/openmrs18/module/reportingsummary/dsdefinition/addDsd.form');">${dsd.name}</a> </td>
		<td>${dsd.code}</td>
		<!-- <td><openmrs:formatDate date="${dsd.createdOn}" type="textbox"/></td>
		<td>${dsd.createdBy}</td> -->
		<td>
			<c:choose>
				<c:when test="${not empty dsd.datasets }">
					<a href="#" onclick="window.open('/openmrs18/moduleResources/reportingsummary/dsdefinition/addDsd.form');" title="Map datasets to this definition">Change Datasets</a>
					&nbsp;&nbsp;<td><a href="#" onclick="ReportingSummary.setOutputType('download');ReportingSummary.showDialog('${ dsd.code}');" >Run</a></td>
				</c:when>
				<c:otherwise>
					<a href="#" onclick="window.open('/openmrs18/moduleResources/reportingsummary/dsdefinition/addDsd.form?dsdCode=${dsd.code}');" title="Map datasets to this definition">Add Datasets</a>
					<td>Download</td>
				</c:otherwise>
			</c:choose>
		</td>
		<td><a href="#" onclick="ReportingSummary.setOutputType('view');ReportingSummary.showDialog('${dsd.code}');" >View</a></td>
	</tr>
</c:forEach>

<tr class="paging-container">
	<td colspan="6"><%@ include file="../paging.jsp" %></td>
</tr>
</table>
<div id="excecuteQuery">
<input type="hidden" id="dsdCode" name="dsdCode" />
<input type="hidden" id="outputType" name="outputType">
<table >
	<tr>
		<td><spring:message code="reportingsummary.DSD.patientId"/><em>*</em></td>
		<td>
			<input type="text" name="patientId" id="patientId" value="${patientId}"/>
		</td>
	</tr>
</table>
</div>
</c:when>
<c:otherwise>
	No DataSetDefinition found.
</c:otherwise>
</c:choose>
</div>
</form>



<%@ include file="/WEB-INF/template/footer.jsp" %>

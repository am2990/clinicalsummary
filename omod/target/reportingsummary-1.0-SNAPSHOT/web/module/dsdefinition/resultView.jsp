<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../template/localHeader.jsp"%>
<%@ include file="../includes/js_css.jsp" %>

<openmrs:require privilege="View DSDefinitions" otherwise="/login.htm" redirect="/module/reportingsummary/viewDefinition.form" />

<spring:message var="pageTitle" code="reportingsummary.DSD.view" scope="page"/>

<h2><spring:message code="reportingsummary.DSD.view"/></h2>	

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>

<br /><br />
<input type="hidden" id="pageId" value="listReportPage" />
<table cellpadding="5" cellspacing="0"  >
	<tr>
	
	<!-- Modified the back link from report.form to listReport.form  -->
		<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Back" onclick="ACT.go('viewDsd.form');"/></td>
	</tr>
</table>
<span class="boxHeader"><spring:message code="CSV Report"/></span>
<div class="box">
<pre>${contents}</pre>
</div>
<%@ include file="/WEB-INF/template/footer.jsp" %>

<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<script>
function setPage(type){window.location.href=openmrsContextPath+'/module/reportingsummary/summaryrenderer.form?type='+type}
</script>
<%@ include file="template/localHeader.jsp"%>
<a href="#" onclick="setPage('xml');">Download Summary XML</a>
<a href="#" onclick="setPage('csv');">Download Summary CSV</a>
<%@ include file="/WEB-INF/template/footer.jsp"%>
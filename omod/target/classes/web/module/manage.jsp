<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<link type="text/css" rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/themes/ui-lightness/jquery-ui.css" />

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/jquery-ui.min.js"></script>
<%@ include file="includes/js_css.jsp" %>
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
<div id="wrapper"> 
	<div id="header"> 
		<h1>jQuery UI Multiselect</h1> 
	</div>
	
	<div id="content">
    <form action="manage.form" method="POST">
      <select id="datasets" class="multiselect" multiple="multiple" name="datasets[]">
        <option value="patientid">PatientID</option>
        <option value="birthdate">BirthDate</option>
		<option value="age">Age</option>
        <option value="gender" selected="selected">Gender</option>
      </select>
      <br/>
      <input type="submit" value="Submit Form"/>
    </form>
    </div>
    <script type="text/javascript"
      src="http://jqueryui.com/themeroller/themeswitchertool/">
    </script>
  </div>
</body> 
</html>



<%@ include file="/WEB-INF/template/footer.jsp"%>

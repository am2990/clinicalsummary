/**
MOTU
*/
ReportingSummary = {
		setAddress : function(add){
			window.location.href = openmrsContextPath + "/module/reportingsummary/"+add;
		},
		checkValue : function()
		{
			var form = jQuery("#form");
			if( jQuery("input[type='checkbox']:checked",form).length > 0 )
			{ 
				if(confirm("Are you sure?"))
				{
					form.submit();
				}
			}
			else
			{
				alert("Please choose objects for deleting");
				return false;
			}
		},
		
		search : function(url, value){
			ACT.go(url+"?"+value+"="+jQuery("#"+value).val());
		},
		onChangeReportDataElement : function(thiz){
			ACT.go("reportDataElement.form?reportId="+jQuery(thiz).val());
		},
		setOutputType : function(type){
			jQuery("#outputType").val(type);
		},
		showDialog : function(dsdId){
			jQuery("#dsdId").val(dsdId);
			jQuery('#excecuteQuery').dialog('open');
		},
		upload : function(){
			jQuery('#uploadBox').dialog('open');
		},
		showQuery : function(queryId){
			jQuery("#queryId").val(queryId);
			jQuery('#excecuteQuery').dialog('open');
		},
		extractMonth: function(date, from){
			jQuery.ajax({
				type : "GET",				
				url : "extractMonth.form",
				data : ({
					date: date
				}),
				success : function(data) {
					if(from){
						jQuery("#fromMonth").html("<b>From month: </b>" + data);
					} else {
						jQuery("#toMonth").html("<b>To month:     </b>" + data);
					}
					
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert("ERROR " + xhr);
				}
			});
		}
		
		
}


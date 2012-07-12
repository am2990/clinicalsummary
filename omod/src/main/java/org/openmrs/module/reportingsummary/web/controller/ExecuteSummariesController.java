/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.reportingsummary.web.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.PatientIdentifierCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
//import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.renderer.CsvReportRenderer;
import org.openmrs.module.reporting.report.renderer.RenderingException;
import org.openmrs.module.reporting.report.renderer.XmlReportRenderer;
import org.openmrs.module.reportingsummary.api.DataSet;
import org.openmrs.module.reportingsummary.api.DataSetDefinition;
import org.openmrs.module.reportingsummary.api.io.util.PatientDatasetDefinitionMaker;
import org.openmrs.module.reportingsummary.api.service.DataSetDefinitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main controller.
 */
@Controller
public class  ExecuteSummariesController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	
	
	
	@RequestMapping(value = "/module/reportingsummary/dsdefinition/executeSummaries", method = RequestMethod.GET)
	public String manage(ModelMap model , HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String view = request.getParameter("view");
		String dsdId = request.getParameter("dsdId");
		Map<String, org.openmrs.module.reporting.dataset.DataSet> mp = null;
		System.out.println("Viewing Jsp"+ id+"view"+view+"dsd "+ dsdId);

		DataSetDefinitionService dsdService=Context.getService(DataSetDefinitionService.class);
		DataSetDefinition dsd = dsdService.getDataSetDefinitionbyId(Integer.parseInt(dsdId));
		List<DataSet> ds = dsdService.listDataSets(dsd.getDsdcode());
		
		
		PatientDataSetDefinition definition=new PatientDataSetDefinition();
		definition = PatientDatasetDefinitionMaker.PatientDatasetDefinition(ds);
		
		PatientIdentifierCohortDefinition ptFilter=new PatientIdentifierCohortDefinition();
//		ptFilter.addTypeToMatch(new PatientIdentifierType(1));
//		ptFilter.setId(Integer.parseInt(id));
		String sqlQuery = "SELECT distinct patient_id FROM patient WHERE patient_id = "+ id;
		SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition(sqlQuery);	
		definition.addRowFilter(sqlCohortDefinition, null);
		
//		AgeCohortDefinition ageFilter=new AgeCohortDefinition();
//		ageFilter.setMaxAge(30);
//		definition.addRowFilter(ageFilter, null);
		
		ReportDefinition reportDefinition = new ReportDefinition();
        reportDefinition.setName("Test Report");
        reportDefinition.addDataSetDefinition("PatientDataSetDefinition", definition, null);

        ReportDefinitionService rs = Context.getService(ReportDefinitionService.class);
        ReportData data = null;
		try {
			data = rs.evaluate(reportDefinition, prepareEvaluationContext());
		} catch (EvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServletOutputStream resp = null;
		
		
		try {
				resp = response.getOutputStream();
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        CsvReportRenderer csvReportRenderer = new CsvReportRenderer();
        
        try {
        	
			csvReportRenderer.render(data, "output:csv", System.out);
			csvReportRenderer.render(data, "output:csv", resp);
			
		} catch (RenderingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		model.addAttribute("contents",  resp);
		return "/module/reportingsummary/dsdefinition/resultView";
 
	}
	
	private EvaluationContext prepareEvaluationContext() {

	    EvaluationContext context = new EvaluationContext();
	    context.addParameterValue("currentDate", new Date());
	    context.setBaseCohort(Context.getCohortService().getCohort(10));

	    return context;
	}
		
}

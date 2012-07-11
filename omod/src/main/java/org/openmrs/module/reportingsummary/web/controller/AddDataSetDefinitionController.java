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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;


import javax.servlet.http.HttpServletRequest;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
//import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.renderer.CsvReportRenderer;
import org.openmrs.module.reporting.report.renderer.RenderingException;
import org.openmrs.module.reportingsummary.api.DataSetDefinition;
import org.openmrs.module.reportingsummary.api.io.util.PatientAttributeConstants;
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
public class  AddDataSetDefinitionController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	
	
	
	@RequestMapping(value = "/module/reportingsummary/dsdefinition/addDsd", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		PatientAttributeConstants.initialize();
		model.addAttribute("patientAttributes", PatientAttributeConstants.listAttributes());
		model.addAttribute("user", Context.getAuthenticatedUser());
	}
	
	@RequestMapping(value = "/module/reportingsummary/dsdefinition/addDsd", method = RequestMethod.POST)
	public String managePost(ModelMap model,HttpServletRequest request) {
		//PatientAttributeConstants.initialize();
		//model.addAttribute("patientAttributes", PatientAttributeConstants.listAttributes());
		
		String[] datasets = request.getParameterValues("datasets[]");
		String name=request.getParameter("DSDName");
		String code=request.getParameter("DSDCode");
		ArrayList<String> dsList =new ArrayList(Arrays.asList(datasets));
		DataSetDefinition dsd = new DataSetDefinition();
		dsd.setDefinitionName(name);
		dsd.setCreator(Context.getAuthenticatedUser());
		dsd.setDsdcode(code);
		DataSetDefinitionService dsdService=Context.getService(DataSetDefinitionService.class);
		dsdService.saveDataSetDefinition(dsd);
		
		for(String p : datasets){
			System.out.println(p);
		}
		
		
		model.addAttribute("user", Context.getAuthenticatedUser());
		return "/module/reportingsummary/manage";
	}
	
}

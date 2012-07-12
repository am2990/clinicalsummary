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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.data.converter.AgeConverter;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.ListConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.PatientIdDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.AgeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportDesignResource;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.renderer.CsvReportRenderer;
import org.openmrs.module.reporting.report.renderer.ExcelTemplateRenderer;
import org.openmrs.module.reporting.report.renderer.RenderingException;
import org.openmrs.module.reporting.report.renderer.SummaryXmlReportRenderer;
import org.openmrs.module.reporting.summary.PatientDataSetDefinitionTest;
import org.openmrs.module.reportingsummary.api.DataSetDefinition;
import org.openmrs.module.reportingsummary.api.io.util.PatientAttributeConstants;
import org.openmrs.module.reportingsummary.api.io.util.PatientDatasetDefinitionMaker;
import org.openmrs.module.reportingsummary.api.service.DataSetDefinitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main controller.
 */
@Controller
public class  ReportingBasedClinicalSummaryModuleManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	
	
	
	@RequestMapping(value = "/module/reportingsummary/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		PatientAttributeConstants.initialize();
		model.addAttribute("patientAttributes", PatientAttributeConstants.listAttributes());
		model.addAttribute("user", Context.getAuthenticatedUser());
	}
	
	@RequestMapping(value = "/module/reportingsummary/dsdefinition/viewDsd", method = RequestMethod.GET)
	public void view(ModelMap model) {
		
		DataSetDefinitionService dsdService=Context.getService(DataSetDefinitionService.class);
		List<DataSetDefinition> dsdefnitions = dsdService.listDataSetDefinition();
		ListIterator<DataSetDefinition> li = dsdefnitions.listIterator();
		while(li.hasNext()){
			DataSetDefinition dsd = new DataSetDefinition();
			System.out.println("DSD List"+ dsd.getDefinitionName()+","+dsd.getDsdcode());
			li.next();
		}
		PatientAttributeConstants.initialize();
		model.addAttribute("dsdefinitions", dsdefnitions);
		model.addAttribute("patientAttributes", PatientAttributeConstants.listAttributes());
		model.addAttribute("user", Context.getAuthenticatedUser());
	}
	
	@RequestMapping(value = "/module/reportingsummary/manage", method = RequestMethod.POST)
	public String managePost(ModelMap model,HttpServletRequest request) {
		//PatientAttributeConstants.initialize();
		//model.addAttribute("patientAttributes", PatientAttributeConstants.listAttributes());
		
		String[] params = request.getParameterValues("datasets[]");
		temp();
		System.out.println(params);
		System.out.println("----------params");
		
		for(String p : params){
			System.out.println(p);
		}
		
		PatientDataSetDefinition definition=new PatientDataSetDefinition();
		definition = PatientDatasetDefinitionMaker.PatientDatasetDefinition(params);
		//definition.addColumn("id", new PatientIdDataDefinition(), StringUtils.EMPTY, new ObjectFormatter());
     
        AgeCohortDefinition ageFilter=new AgeCohortDefinition();
//        ageFilter.setMinAge(104);
        definition.addRowFilter(ageFilter, null);
        
        
        
        
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

        CsvReportRenderer csvReportRenderer = new CsvReportRenderer();
        try {
			csvReportRenderer.render(data, "output:csv", System.out);
		} catch (RenderingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
		
		model.addAttribute("user", Context.getAuthenticatedUser());
		return "/module/reportingsummary/manage";
	}
	

	
	private void temp(){
		PatientDataSetDefinition definition = new PatientDataSetDefinition();
        definition.addColumn("id", new PatientIdDataDefinition(), StringUtils.EMPTY, new ObjectFormatter());

        //ListConverter listConverter = new ListConverter();
        //listConverter.setMaxNumberOfItems(1);

        //PatientIdentifierDataDefinition preferredIdentifier = new PatientIdentifierDataDefinition();
        //definition.addColumn("identifier", preferredIdentifier, StringUtils.EMPTY, listConverter);

        //definition.addColumn("name", new PreferredNameDataDefinition(), StringUtils.EMPTY, new ObjectFormatter("{familyName}, {givenName}"));

        AgeDataDefinition ageOnDate = new AgeDataDefinition();
        ageOnDate.addParameter(new Parameter("effectiveDate", "effective date", Date.class));
        definition.addColumn("age", ageOnDate, "effectiveDate=${currentDate}", new AgeConverter());
        
        AgeCohortDefinition ageFilter=new AgeCohortDefinition();
        ageFilter.setMinAge(104);
        definition.addRowFilter(ageFilter, null);
        
        definition.addColumn("birthdate", new BirthdateDataDefinition(), StringUtils.EMPTY, new BirthdateConverter("dd-MMM-yyyy"));
        definition.addColumn("gender", new GenderDataDefinition(), StringUtils.EMPTY, new ObjectFormatter());

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

        CsvReportRenderer csvReportRenderer = new CsvReportRenderer();
        try {
			csvReportRenderer.render(data, "output:csv", System.out);
		} catch (RenderingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

private EvaluationContext prepareEvaluationContext() {

    EvaluationContext context = new EvaluationContext();
    context.addParameterValue("currentDate", new Date());
    context.setBaseCohort(Context.getCohortService().getCohort(10));

    return context;
}
}

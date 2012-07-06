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
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonName;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.PatientSetService;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.Age;
import org.openmrs.module.reporting.data.converter.AgeConverter;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ListConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.converter.PropertyConverter;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDatetimeDataDefinition;
import org.openmrs.module.reporting.data.encounter.definition.EncounterIdDataDefinition;
import org.openmrs.module.reporting.data.encounter.definition.EncounterLocationDataDefinition;
import org.openmrs.module.reporting.data.encounter.definition.EncounterProviderDataDefinition;
import org.openmrs.module.reporting.data.encounter.definition.EncounterTypeDataDefinition;
import org.openmrs.module.reporting.data.obs.definition.ObsCodedDataDefinition;
import org.openmrs.module.reporting.data.obs.definition.ObsDateDataDefinition;
import org.openmrs.module.reporting.data.obs.definition.ObsDatetimeDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.AgeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
import org.openmrs.module.reporting.dataset.definition.EncounterDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.ObsDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.query.encounter.definition.BasePatientEncounterQuery;
import org.openmrs.module.reporting.query.obs.definition.CodedObsQuery;
import org.openmrs.module.reporting.query.obs.definition.DateObsQuery;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportDesignResource;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.renderer.CsvReportRenderer;
import org.openmrs.module.reporting.report.renderer.ExcelTemplateRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/module/reportingsummary/summary/generateSummaries")
public class GenerateSummariesController {

    private static final Log log = LogFactory.getLog(GenerateSummariesController.class);

    @RequestMapping(method = RequestMethod.GET)
    public void prepare(final ModelMap map) {
        // TODO: empty method implementation for future use
    }

    @RequestMapping(method = RequestMethod.POST)
    public void process(final @RequestParam(required = true, value = "template") MultipartFile template,
                        final HttpServletResponse response) throws IOException, EvaluationException {

        PatientDataSetDefinition definition = new PatientDataSetDefinition();

        definition.addColumn("id", new PatientIdDataDefinition(), StringUtils.EMPTY, new ObjectFormatter());

        ListConverter listConverter = new ListConverter();
        listConverter.setMaxNumberOfItems(1);

        PatientIdentifierDataDefinition preferredIdentifier = new PatientIdentifierDataDefinition();
        definition.addColumn("identifier", preferredIdentifier, StringUtils.EMPTY, listConverter);

        definition.addColumn("name", new PreferredNameDataDefinition(), StringUtils.EMPTY, new ObjectFormatter("{familyName}, {givenName}"));

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

        final ReportDesign design = new ReportDesign();
        design.setName("Test Report Design With Excel Template Renderer");
        design.setReportDefinition(reportDefinition);
        design.setRendererType(ExcelTemplateRenderer.class);

        Properties properties = new Properties();
        properties.put("repeatingSections", "sheet:1,dataset:PatientDataSetDefinition");
        design.setProperties(properties);

        ReportDesignResource resource = new ReportDesignResource();
        resource.setName("excel-template.xls");
        Properties props = new Properties();
        InputStream inputStream = template.getInputStream();
        resource.setContents(IOUtils.toByteArray(inputStream));
        IOUtils.closeQuietly(inputStream);
        design.addResource(resource);

        ExcelTemplateRenderer renderer = new ExcelTemplateRenderer() {
            public ReportDesign getDesign(String argument) {
                return design;
            }
        };

        ReportDefinitionService rs = Context.getService(ReportDefinitionService.class);
        ReportData data = rs.evaluate(reportDefinition, prepareEvaluationContext());

        CsvReportRenderer csvReportRenderer = new CsvReportRenderer();
        csvReportRenderer.render(data, "output:csv", System.out);

        File file = File.createTempFile("excel", "summary");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        renderer.render(data, "output:xls", bufferedOutputStream);
        bufferedOutputStream.close();

        response.setHeader("Content-Disposition", "attachment; filename=patient-summary.xls");
        response.setContentType("application/vnd.ms-excel");
        response.setContentLength((int) file.length());

        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
        if (file.delete())
            log.info("Temporary file deleted!");
    }

    private EvaluationContext prepareEvaluationContext() {

        EvaluationContext context = new EvaluationContext();
        context.addParameterValue("currentDate", new Date());
        context.setBaseCohort(Context.getCohortService().getCohort(10));

        return context;
    }

}

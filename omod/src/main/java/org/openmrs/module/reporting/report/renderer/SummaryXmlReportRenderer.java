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
package org.openmrs.module.reporting.report.renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.common.Localized;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.renderer.AbstractReportRenderer;
import org.openmrs.module.reporting.report.renderer.RenderingException;
import org.openmrs.module.reporting.report.renderer.RenderingMode;
import org.openmrs.module.reporting.report.renderer.SummaryXmlReportRenderer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * ReportRenderer that renders to a delimited text file
 */
@Handler
@Localized("reporting.SummaryXmlReportRenderer")
public class SummaryXmlReportRenderer extends AbstractReportRenderer {

    private static final Log log = LogFactory.getLog(SummaryXmlReportRenderer.class);

    /**
     * @see org.openmrs.module.reporting.report.renderer.ReportRenderer#getFilename(org.openmrs.module.reporting.report.definition.ReportDefinition, String)
     */
    public String getFilename(ReportDefinition reportDefinition, String argument) {
        return reportDefinition.getName() + ".xml";
    }

    /**
     * @see org.openmrs.module.reporting.report.renderer.ReportRenderer#getRenderingModes(org.openmrs.module.reporting.report.definition.ReportDefinition)
     */
    public Collection<RenderingMode> getRenderingModes(ReportDefinition reportDefinition) {
        Collection<RenderingMode> renderingModes = null;
        if (reportDefinition.getDataSetDefinitions() != null && reportDefinition.getDataSetDefinitions().size() < 1)
            renderingModes = Collections.singleton(new RenderingMode(this, this.getLabel(), null, Integer.MIN_VALUE));
        return renderingModes;
    }

    /**
     * @see org.openmrs.module.reporting.report.renderer.ReportRenderer#render(org.openmrs.module.reporting.report.ReportData, String, java.io.OutputStream)
     */
    public void render(ReportData results, String argument, OutputStream out) throws IOException, RenderingException {
        Writer w = new OutputStreamWriter(out, "UTF-8");
        DataSet dataset = results.getDataSets().values().iterator().next();
        
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = builder.newDocument();
        Element rooterElement = document.createElement("Summaries");
        document.appendChild(rooterElement);
        for (DataSetRow dataSetRow : dataset) {
                Element rootElement = document.createElement("patientSummary");
                rooterElement.appendChild(rootElement);
                renderDataSetRow(dataSetRow, document, rootElement);
                
        }
        serialize(document, out);
    }

    private void renderDataSetColumn(final Object columnValue, final Document document, final Element parent) {
        if (columnValue != null)
            if (columnValue instanceof Collection) {
                Collection collections = (Collection) columnValue;
                for (Object collection : collections)
                    renderDataSetColumn(collection, document, parent);
            } else if (columnValue instanceof DataSetRow) {
                DataSetRow internalDataSetRow = (DataSetRow) columnValue;
                renderDataSetRow(internalDataSetRow, document, parent);
            } else {
                String value = columnValue.toString();
                if (columnValue instanceof Cohort)
                    value = Integer.toString(((Cohort) columnValue).size());
                parent.appendChild(document.createTextNode(value));
            }
    }

    private void renderDataSetRow(final DataSetRow dataSetRow, final Document document, final Element parent) {
        for (DataSetColumn dataSetColumn : dataSetRow.getColumnValues().keySet()) {
            // create the column element
            Element columnElement = document.createElement(dataSetColumn.getLabel());
            parent.appendChild(columnElement);
            // process the value
            Object columnValue = dataSetRow.getColumnValue(dataSetColumn);
            renderDataSetColumn(columnValue, document, columnElement);
        }
    }

    private void serialize(final Document document, final OutputStream outputStream) throws IOException {
        OutputFormat format = new OutputFormat();
        format.setIndenting(true);
        format.setLineWidth(150);

        XMLSerializer xmlSerializer = new XMLSerializer(outputStream, format);
        xmlSerializer.serialize(document);
    }

    public String getRenderedContentType(ReportDefinition schema, String argument) {
        return "text/xml";
    }
}

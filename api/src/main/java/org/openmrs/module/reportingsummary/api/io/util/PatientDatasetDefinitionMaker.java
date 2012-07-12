package org.openmrs.module.reportingsummary.api.io.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.patient.definition.PatientDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reportingsummary.api.DataSet;

public class PatientDatasetDefinitionMaker {

	PatientDataSetDefinition pdd;
	
	public static PatientDataSetDefinition PatientDatasetDefinition(List<DataSet> attributes) {
		
		PatientDataSetDefinition definition = new PatientDataSetDefinition();
		
		for(DataSet attribute: attributes){
			PatientAttributeConstants.initialize();
			PatientAttributeConstants.Attributes.get(attribute);
			System.out.println(attribute+" "+PatientAttributeConstants.Attributes.get(attribute.getDatasetName()));
			definition.addColumn(
					PatientAttributeConstants.Attributes.get(attribute.getDatasetName()),
					PatientAttributeConstants.DataDefinitions.get(attribute.getDatasetName()),
					StringUtils.EMPTY,
					PatientAttributeConstants.Formatter.get(attribute.getDatasetName())
			);
			
		}
		
		
		return definition;
	} 
	
}

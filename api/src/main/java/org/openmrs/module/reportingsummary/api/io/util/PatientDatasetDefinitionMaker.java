package org.openmrs.module.reportingsummary.api.io.util;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.patient.definition.PatientDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reportingsummary.api.PatientAttributeConstants;

public class PatientDatasetDefinitionMaker {

	PatientDataSetDefinition pdd;
	
	public static PatientDataSetDefinition PatientDatasetDefinition(String[] attributes) {
		
		PatientDataSetDefinition definition = new PatientDataSetDefinition();
		
		for(String attribute: attributes){
			PatientAttributeConstants.initialize();
			PatientAttributeConstants.Attributes.get(attribute);
			System.out.println(attribute+" "+PatientAttributeConstants.Attributes.get(attribute));
			definition.addColumn(
					PatientAttributeConstants.Attributes.get(attribute),
					PatientAttributeConstants.DataDefinitions.get(attribute),
					StringUtils.EMPTY,
					PatientAttributeConstants.Formatter.get(attribute)
			);
			
		}
		
		
		return definition;
	} 
	
}

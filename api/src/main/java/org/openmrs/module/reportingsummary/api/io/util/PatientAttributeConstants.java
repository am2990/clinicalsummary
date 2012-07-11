package org.openmrs.module.reportingsummary.api.io.util;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.AgeConverter;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.PatientIdDataDefinition;
import org.openmrs.module.reporting.data.person.definition.AgeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;

public final class PatientAttributeConstants {
	
private static final String patientid="id";
private static final String gender="gender";
private static final String birthdate="birthdate";
private static final String age="age";
public static HashMap<String,String> Attributes = new HashMap<String,String>();
public static HashMap<String, DataDefinition > DataDefinitions = new HashMap<String, DataDefinition>();
public static HashMap<String, DataConverter> Formatter = new HashMap<String, DataConverter>();

public static void initialize(){
	Attributes.put("patientid", patientid);
	Attributes.put("gender", gender);
	Attributes.put("age", age);
	Attributes.put("birthdate", birthdate);
	DataDefinitions.put("patientid", new PatientIdDataDefinition());
	DataDefinitions.put("gender", new GenderDataDefinition());
	DataDefinitions.put("birthdate", new BirthdateDataDefinition());
	DataDefinitions.put("age", new AgeDataDefinition());
	Formatter.put("patientid", new ObjectFormatter());
	Formatter.put("gender", new ObjectFormatter());
	Formatter.put("birthdate", new BirthdateConverter("dd-MMM-yyyy"));
	Formatter.put("age", new AgeConverter());
}

public static Map<String, String> getAllAttributes(){
	return Attributes;
}

public static ArrayList<String> listAttributes(){
	return new ArrayList<String>(Attributes.keySet());
}

}

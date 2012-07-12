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
package org.openmrs.module.reportingsummary.api.service;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.reportingsummary.api.DataSet;
import org.openmrs.module.reportingsummary.api.DataSetDefinition;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(SummaryService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface DataSetDefinitionService extends OpenmrsService {
	
	DataSetDefinition saveDataSetDefinition(DataSetDefinition DataSetDefinition) throws APIException;
	
	List<DataSetDefinition> listDataSetDefinition() throws APIException;
	
	DataSetDefinition saveDataSetDefinition(String Code,String name,
			ArrayList<String> dataSets) throws APIException;

	DataSetDefinition getDataSetDefinitionbyId(Integer id) throws APIException;

	DataSetDefinition getDataSetDefinitionbyName(String name) throws APIException;

	void deleteDataSetDefinition(DataSetDefinition dataSetDefinition) throws APIException;

	DataSet saveDataSet(DataSet DataSet) throws APIException;
    
    List<DataSet> listDataSets(Integer dsdCode) throws APIException;

    List<DataSet> listDataSets(String dsd_code) throws APIException;
    
    DataSet getDataSet(Integer dsdCode,String name) throws APIException;

	void deleteDataSet(DataSet ds) throws APIException;

}
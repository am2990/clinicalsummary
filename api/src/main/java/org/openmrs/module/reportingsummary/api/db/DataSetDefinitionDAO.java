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

package org.openmrs.module.reportingsummary.api.db;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.reportingsummary.api.DataSet;
import org.openmrs.module.reportingsummary.api.DataSetDefinition;
import org.openmrs.module.reportingsummary.api.InvertedIndex;
import org.springframework.transaction.annotation.Transactional;

public interface DataSetDefinitionDAO {
	

	DataSetDefinition saveDataSetDefinition(DataSetDefinition DataSetDefiniion) throws DAOException;

    
    List<DataSetDefinition> listDataSetDefinition() throws DAOException;


	DataSetDefinition saveDataSetDefinition(String Code,String name,
			ArrayList<String> dataSets) throws DAOException;


	DataSetDefinition getDataSetDefinitionbyId(Integer id) throws DAOException;


	DataSetDefinition getDataSetDefinitionbyName(String name)
			throws DAOException;


	
	
	//Datasets
	
	DataSet saveDataSet(DataSet DataSet) throws DAOException;
    
    List<DataSetDefinition> listDataSet(Integer dsdCode) throws DAOException;

    DataSet getDataSet(Integer dsdCode,String name) throws DAOException;

	List<DataSet> listDataSets(String dsd_code) throws DAOException;


	void deleteDataSet(DataSet ds) throws DAOException;
	
	void deleteDataSetDefinition(DataSetDefinition dataSetDefinition)
			throws DAOException;
	

}

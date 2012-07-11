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

package org.openmrs.module.reportingsummary.api.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.reportingsummary.api.DataSet;
import org.openmrs.module.reportingsummary.api.DataSetDefinition;
import org.openmrs.module.reportingsummary.api.db.DataSetDefinitionDAO;
import org.openmrs.module.reportingsummary.api.service.DataSetDefinitionService;

public class DataSetDefinitionServiceImpl extends BaseOpenmrsService implements DataSetDefinitionService {

    private DataSetDefinitionDAO dao;
	@Override
	public DataSetDefinition saveDataSetDefinition(DataSetDefinition dsd)
			throws APIException {
		return dao.saveDataSetDefinition(dsd);
	}

	@Override
	public List<DataSetDefinition> listDataSetDefinition() throws APIException {
		// TODO Auto-generated method stub
		return dao.listDataSetDefinition();
	}

	@Override
	public DataSetDefinition saveDataSetDefinition(String Code,String name,
			ArrayList<String> dataSets) throws APIException {
		// TODO Auto-generated method stub
		return dao.saveDataSetDefinition(Code, name, dataSets);
	}

	@Override
	public DataSetDefinition getDataSetDefinitionbyId(Integer id)
			throws APIException {
		return dao.getDataSetDefinitionbyId(id);
	}

	@Override
	public DataSetDefinition getDataSetDefinitionbyName(String name)
			throws APIException {
		return dao.getDataSetDefinitionbyName(name);
	}

	@Override
	public void deleteDataSetDefinition(DataSetDefinition dataSetDefinition)
			throws APIException {
		dao.deleteDataSetDefinition(dataSetDefinition);
		
	}

	@Override
	public DataSet saveDataSet(DataSet DataSet) throws DAOException {
		return dao.saveDataSet(DataSet);
	}

	@Override
	public List<DataSetDefinition> listDataSet(Integer dsdCode)
			throws DAOException {
		
		return dao.listDataSet(dsdCode);
	}

	@Override
	public DataSet getDataSet(Integer dsdCode, String name) throws DAOException {
		// TODO Auto-generated method stub
		return dao.getDataSet(dsdCode, name);
	}

	@Override
	public void deleteDataSet(Integer dsdCode, String name) throws DAOException {
		dao.deleteDataSet(dsdCode, name);
	}
}

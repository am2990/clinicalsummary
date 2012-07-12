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

package org.openmrs.module.reportingsummary.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.reportingsummary.api.DataSet;
import org.openmrs.module.reportingsummary.api.DataSetDefinition;
import org.openmrs.module.reportingsummary.api.InvertedIndex;
import org.openmrs.module.reportingsummary.api.db.DataSetDefinitionDAO;
import org.openmrs.module.reportingsummary.api.db.InvertedIndexDAO;
import org.openmrs.module.reportingsummary.api.service.DataSetDefinitionService;

public class HibernateDataSetDefinitionDAO implements DataSetDefinitionDAO {

    private SessionFactory sessionFactory;

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }


	@Override
	public DataSetDefinition saveDataSetDefinition(DataSetDefinition dsd)
			throws DAOException {
		return (DataSetDefinition) sessionFactory.getCurrentSession().merge(dsd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataSetDefinition> listDataSetDefinition() throws DAOException {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DataSetDefinition.class);
//		if(StringUtils.isNotBlank(name)){
//			criteria.add(Restrictions.like("name", "%"+name+"%"));
//		}
//		if(max > 0){
//			criteria.setFirstResult(min).setMaxResults(max);
//		}
		List<DataSetDefinition> l = criteria.list();
		
		return l;
	}
	
	
	/**
	 * DAO to save DataSetDefinition To db
     *
     * @param Code of the DataSetDefinition,ArrayList of associated  
     * @return the saved DataSetDefinition,
     * @throws org.openmrs.api.db.DAOException
     *          when the service call is failing
	 */
	@Override
	public 	DataSetDefinition saveDataSetDefinition(String Code,String name, ArrayList<String> dataSets)
			throws DAOException {
		ListIterator<String> li = dataSets.listIterator();
		String dsname;
		int i=1;
		while(li.hasNext()){
			DataSetDefinitionService dsdService=Context.getService(DataSetDefinitionService.class);
			DataSet ds=new DataSet();
			dsname = (String) li.next();
			ds.setDatasetName(dsname);
			ds.setDsdCode(Code);
			ds.setDatasetCode(i);
			i++;
			System.out.println("Element 1 = " + ds.getDatasetName());
			dsdService.saveDataSet(ds);
//			dsd.setCreatedOn(new java.util.Date());
//			dsd.setCreator(Context.getAuthenticatedUser().getGivenName());
		}
		DataSetDefinition dsd=new DataSetDefinition();
		dsd.setDefinitionName(name);
		dsd.setDsdcode(Code);
		sessionFactory.getCurrentSession().merge(dsd);
		return null;
	}
	
	
	@Override
	public DataSetDefinition getDataSetDefinitionbyId(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DataSetDefinition.class);
		criteria.add(Restrictions.eq("id", id));
		DataSetDefinition dataSetDefinition = (DataSetDefinition) criteria.uniqueResult();
		return dataSetDefinition;
	}
	
	@Override
	public DataSetDefinition getDataSetDefinitionbyName(String name) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DataSetDefinition.class);
		criteria.add(Restrictions.eq("name", name));
		DataSetDefinition dataSetDefinition = (DataSetDefinition) criteria.uniqueResult();
		return dataSetDefinition;
	}


	@Override
	public DataSet saveDataSet(DataSet DataSet) throws DAOException {
		return (DataSet) sessionFactory.getCurrentSession().merge(DataSet);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DataSet> listDataSets(String name)
			throws DAOException {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DataSet.class);
		System.out.println("code inside hibernate "+ name);
		if(StringUtils.isNotBlank(name)){
			criteria.add(Expression.eq("dsdCode", name));
			criteria.addOrder(Order.asc("datasetCode"));
		}
		
		List<DataSet> l = criteria.list();
		
		return l;
	}

	@Override
	public List<DataSetDefinition> listDataSet(Integer dsdCode)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public DataSet getDataSet(Integer dsdCode, String name) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteDataSet(DataSet ds) throws DAOException {
		
		sessionFactory.getCurrentSession().delete(ds);
		
	}
	
	@Override
	public void deleteDataSetDefinition(DataSetDefinition dataSetDefinition) throws DAOException {
	
		sessionFactory.getCurrentSession().delete(dataSetDefinition);
	}
	
	
}

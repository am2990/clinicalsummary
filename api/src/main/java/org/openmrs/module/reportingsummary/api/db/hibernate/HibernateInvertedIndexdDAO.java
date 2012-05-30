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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.reportingsummary.api.InvertedIndex;
import org.openmrs.module.reportingsummary.api.db.InvertedIndexDAO;

public class HibernateInvertedIndexdDAO implements InvertedIndexDAO {

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

    /**
     * DAO to save the inverted record into the database
     *
     * @param invertedIndex the inverted index to be saved
     * @return the saved inverted index
     * @throws org.openmrs.api.db.DAOException
     *          when the service call is failing
     */
    @Override
    public InvertedIndex saveInvertedIndex(InvertedIndex invertedIndex) throws DAOException {
        sessionFactory.getCurrentSession().saveOrUpdate(invertedIndex);
        return invertedIndex;
    }

    /**
     * DAO to get the inverted index record based on the internal id of the record
     *
     * @param id the internal id
     * @return the inverted index or null if no record have the matching internal id
     * @throws org.openmrs.api.db.DAOException
     *          when the service call is failing
     */
    @Override
    public InvertedIndex getInvertedIndex(Integer id) throws DAOException {
        return (InvertedIndex) sessionFactory.getCurrentSession().get(InvertedIndex.class, id);
    }

    /**
     * DAO to get the inverted index record based on the uuid of the record
     *
     * @param uuid the record uuid
     * @return the inverted index or null if no record have the matching uuid
     * @throws org.openmrs.api.db.DAOException
     *          when the service call is failing
     */
    @Override
    public InvertedIndex getInvertedIndexByUuid(String uuid) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InvertedIndex.class);
        criteria.add(Restrictions.eq("uuid", uuid));
        criteria.add(Restrictions.eq("voided", Boolean.FALSE));
        return (InvertedIndex) criteria.uniqueResult();
    }

    /**
     * DAO to get all inverted indexes for a patient
     *
     * @param patient the patient
     * @return the list of all inverted indexes associated with the patient
     * @throws org.openmrs.api.db.DAOException
     *          when the service call is failing
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<InvertedIndex> getPatientInvertedIndexes(Patient patient) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InvertedIndex.class);
        criteria.add(Restrictions.eq("patient", patient));
        criteria.add(Restrictions.eq("voided", Boolean.FALSE));
        return criteria.list();
    }
}

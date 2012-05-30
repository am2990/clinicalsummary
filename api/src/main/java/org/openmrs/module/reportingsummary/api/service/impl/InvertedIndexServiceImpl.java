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

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.reportingsummary.api.InvertedIndex;
import org.openmrs.module.reportingsummary.api.db.InvertedIndexDAO;
import org.openmrs.module.reportingsummary.api.service.InvertedIndexService;

public class InvertedIndexServiceImpl extends BaseOpenmrsService implements InvertedIndexService {

    private InvertedIndexDAO invertedIndexDAO;

    /**
     * @return the invertedIndexDAO
     */
    public InvertedIndexDAO getInvertedIndexDAO() {
        return invertedIndexDAO;
    }

    /**
     * @param invertedIndexDAO the invertedIndexDAO
     */
    public void setInvertedIndexDAO(final InvertedIndexDAO invertedIndexDAO) {
        this.invertedIndexDAO = invertedIndexDAO;
    }

    /**
     * Service to save the inverted record into the database
     *
     * @param invertedIndex the inverted index to be saved
     * @return the saved inverted index
     * @throws org.openmrs.api.APIException when the service call is failing
     */
    @Override
    public InvertedIndex saveInvertedIndex(final InvertedIndex invertedIndex) throws APIException {
        return invertedIndexDAO.saveInvertedIndex(invertedIndex);
    }

    /**
     * Service to get the inverted index record based on the internal id of the record
     *
     * @param id the internal id
     * @return the inverted index or null if no record have the matching internal id
     * @throws org.openmrs.api.APIException when the service call is failing
     */
    @Override
    public InvertedIndex getInvertedIndex(final Integer id) throws APIException {
        return invertedIndexDAO.getInvertedIndex(id);
    }

    /**
     * Service to get the inverted index record based on the uuid of the record
     *
     * @param uuid the record uuid
     * @return the inverted index or null if no record have the matching uuid
     * @throws org.openmrs.api.APIException when the service call is failing
     */
    @Override
    public InvertedIndex getInvertedIndexByUuid(final String uuid) throws APIException {
        return invertedIndexDAO.getInvertedIndexByUuid(uuid);
    }

    /**
     * Service to get all inverted indexes for a patient
     *
     * @param patient the patient
     * @return the list of all inverted indexes associated with the patient
     * @throws org.openmrs.api.APIException when the service call is failing
     */
    @Override
    public List<InvertedIndex> getPatientInvertedIndexes(final Patient patient) throws APIException {
        return invertedIndexDAO.getPatientInvertedIndexes(patient);
    }
}

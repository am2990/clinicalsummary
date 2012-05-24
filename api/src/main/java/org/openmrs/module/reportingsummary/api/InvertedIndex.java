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

package org.openmrs.module.reportingsummary.api;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.module.reporting.dataset.definition.RowPerObjectDataSetDefinition;

/**
 * Inverted table to map between a summary and a patient
 */
public class InvertedIndex extends BaseOpenmrsData implements Serializable {

    private Integer id;

    private Patient patient;

    private RowPerObjectDataSetDefinition definition;

    private User generatedBy;

    private Date generatedOn;

    /**
     * Get the record internal identifier
     *
     * @return the identifier
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Set the record internal identifier
     *
     * @param id the identifier
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the patient for this inverted index entry
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Set the patient for this inverted index entry
     *
     * @param patient the patient
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Get the definition (template) for this inverted index entry
     *
     * @return the definition
     */
    public RowPerObjectDataSetDefinition getDefinition() {
        return definition;
    }

    /**
     * Set the definition (template) for this inverted index entry
     *
     * @param definition the definition
     */
    public void setDefinition(RowPerObjectDataSetDefinition definition) {
        this.definition = definition;
    }

    /**
     * Get the user who generated the summary
     *
     * @return the user who generated the summary
     */
    public User getGeneratedBy() {
        return generatedBy;
    }

    /**
     * Set the user who generated the summary
     *
     * @param generatedBy the user who generated the summary
     */
    public void setGeneratedBy(User generatedBy) {
        this.generatedBy = generatedBy;
    }

    /**
     * Get the date when the summary was generated
     *
     * @return the date when the summary was generated
     */
    public Date getGeneratedOn() {
        return generatedOn;
    }

    /**
     * Set the date when the summary was generated
     *
     * @param generatedOn the date when the summary was generated
     */
    public void setGeneratedOn(Date generatedOn) {
        this.generatedOn = generatedOn;
    }
}

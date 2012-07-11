package org.openmrs.module.reportingsummary.api;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.module.reporting.dataset.definition.RowPerObjectDataSetDefinition;

public class DataSetDefinition extends BaseOpenmrsData implements Serializable{
	
	private Integer id;

    private String definitionName;

    private String dsdcode;
    
    
    public Integer getId() {
		return id;
	}

    public void setId(Integer id) {
		this.id = id;
	}
    
    public String getDefinitionName() {
		return definitionName;
	}

    public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
    
    public String getDsdcode() {
		return dsdcode;
	}

    public void setDsdcode(String dsdcode) {
		this.dsdcode = dsdcode;
	}

    
}

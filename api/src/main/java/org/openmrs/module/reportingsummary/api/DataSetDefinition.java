package org.openmrs.module.reportingsummary.api;

import java.io.Serializable;


public class DataSetDefinition implements Serializable{
	
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

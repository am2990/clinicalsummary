package org.openmrs.module.reportingsummary.api;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.module.reporting.dataset.definition.RowPerObjectDataSetDefinition;

public class DataSet extends BaseOpenmrsData implements Serializable{
	
	private Integer id;

    private String datasetName;

    private int datasetCode;
    
    private String dsdCode;
    
    public Integer getId() {
		return id;
	}

    public void setId(Integer id) {
		this.id = id;
	}
    
    public String getDatasetName() {
		return datasetName;
	}

    public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}
    
    public int getDatasetcode() {
		return datasetCode;
	}

    public void setDatasetcode(int datasetCode) {
		this.datasetCode = datasetCode;
	}
    
    public String getDsdCode() {
		return dsdCode;
	}

    public void setDsdCode(String dsdCode) {
		this.dsdCode = dsdCode;
	}

    
}

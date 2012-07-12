package org.openmrs.module.reportingsummary.api;

import java.io.Serializable;
import org.openmrs.BaseOpenmrsData;



public class DataSet implements Serializable{
	
	private Integer id;

    private String datasetName;

    private Integer datasetCode;
    
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
    
    public Integer getDatasetCode() {
		return datasetCode;
	}

    public void setDatasetCode(Integer datasetCode) {
		this.datasetCode = datasetCode;
	}
    
    public String getDsdCode() {
		return dsdCode;
	}

    public void setDsdCode(String dsdCode) {
		this.dsdCode = dsdCode;
	}

    
}

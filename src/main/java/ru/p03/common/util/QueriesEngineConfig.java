/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.common.util;

import java.util.Map;

/**
 *
 * @author altmf
 */
public class QueriesEngineConfig {
    private String unitName;
    private String dbUrl;
    private Map properties;

    public QueriesEngineConfig(String unitName, String dbUrl, Map properties){
        this.unitName = unitName;
        this.dbUrl = dbUrl;
        this.properties = properties;
        this.properties.put("javax.persistence.jdbc.url", dbUrl); 
    }
    /**
     * @return the unitName
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * @param unitName the unitName to set
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * @return the dbUrl
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * @param dbUrl the dbUrl to set
     */
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * @return the properties
     */
    public Map getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(Map properties) {
        this.properties = properties;
    }
    
}

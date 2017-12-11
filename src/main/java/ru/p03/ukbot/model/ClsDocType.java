/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.ukbot.model;

import java.util.Arrays;
import java.util.List;
import ru.p03.classifier.model.Classifier;
import ru.p03.classifier.model.DocTypeable;

/**
 *
 * @author timofeevan
 */
public class ClsDocType extends Classifier implements DocTypeable{

    private String code;
    private Long id;

    public  ClsDocType(){
        
    }
    
    public  ClsDocType(String code, Long id){
        this.code = code;
        this.id = id;
    }
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    public static List<ClsDocType> types(){
        return Arrays.asList(
                new ClsDocType(ACTION, 1L));
    }

    @Override
    public Integer getIsDeleted() {
        return 0;
    }

    @Override
    public void setIsDeleted(Integer isDeleted) {
        
    }
}

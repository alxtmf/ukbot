/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.classifier.model;

/**
 *
 * @author altmf
 */
public interface DocTypeable {
    
    public static final String ACTION = "Action";
    
    String getCode();
    Long getId();
}

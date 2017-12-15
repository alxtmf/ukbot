/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.classifier.model;

import java.io.Serializable;

/**
 *
 * @author timofeevan
 */
public abstract class Classifier implements IBean, Serializable {

    private static final long serialVersionUID = 1L;

    public Classifier() {

    }

    public abstract Long getId();

    public abstract Integer getIsDeleted();

    public abstract void setIsDeleted(Integer isDeleted);

    @Override
    public abstract void setId(Long id);
//
//    @Override
//	public int hashCode() {
//		return this.getId() != null ? this.getId().hashCode() : 0;
//	}
//
//	@Override
//	public boolean equals(Object object) {
//		if (this == object) {
//			return true;
//		}
//		if (object == null || getClass() != object.getClass()) {
//			return false;
//		}
//
//		Classifier classifier = (Classifier) object;
//        if(this.getId() != null){
//            return this.getId().equals(classifier.getId());
//        } else {
//            return (classifier.getId() == null);
//        }
//	}

}

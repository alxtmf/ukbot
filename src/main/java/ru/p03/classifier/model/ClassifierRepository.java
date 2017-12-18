/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.classifier.model;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author timofeevan
 */
public interface ClassifierRepository {
    /**
	 * Adds a classifier.
	 *
	 * @param classifier
	 *            {@link Classifier}
	 */
	<T extends Classifier> void add(@Nonnull T classifier);

	/**
	 * Finds all classifiers.
	 *
	 * @param clazz
	 *            the class of the classifier
	 * @return the list of {@link Classifier}
	 */
	@Nullable <T extends Classifier> List<T> find(@Nonnull Class<T> clazz);
        
        @Nullable <T extends Classifier> T find(@Nonnull Class<T> clazz, @Nonnull Long id);
        
        @Nonnull <T extends Classifier> List<T> find(@Nonnull Class<T> clazz, boolean isDeleted);
	
	@Nonnull <T extends Classifier> List<T> getAll(@Nonnull Class<T> clazz);
        
        @Nonnull <T extends Classifier> List<T> getAll(@Nonnull Class<T> clazz, boolean isDeleted);

	@Nullable <T> Classifier findDocumentType(@Nonnull Class<T> clazz, @Nonnull String code);
        
        <T extends Classifier> void delete(@Nonnull Class<T> clazz, @Nonnull Long id) throws Exception;
        
        <T extends Classifier> void create(@Nonnull T object);

        <T extends Classifier> void edit(@Nonnull T object) throws Exception;

}

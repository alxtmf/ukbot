/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.inject.provider;

import ru.p03.common.util.QueriesEngineConfig;
import com.google.inject.Inject;
import com.google.inject.Provider;
import ru.p03.common.util.QueriesEngine;

/**
 *
 * @author altmf
 */
public class QueriesEngineProvider implements Provider<QueriesEngine>{
    private final QueriesEngineConfig config;
    
    private final QueriesEngine dao;
    
    @Inject
    public QueriesEngineProvider(QueriesEngineConfig config){
        this.config = config;        
        dao = QueriesEngine.instance(config.getUnitName(), config.getProperties());
    }

    @Override
    public QueriesEngine get() {
        return dao;
    }
}

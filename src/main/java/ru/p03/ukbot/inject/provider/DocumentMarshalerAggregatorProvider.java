/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.inject.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import ru.p03.bot.document.spi.DocumentMarshalerAggregator;
import ru.p03.bot.document.spi.DocumentMarshalerAggregatorConfig;

/**
 *
 * @author altmf
 */
public class DocumentMarshalerAggregatorProvider implements Provider<DocumentMarshalerAggregator>{
    
    private final DocumentMarshalerAggregator marshalFactory = new DocumentMarshalerAggregator();
    
    @Inject
    public DocumentMarshalerAggregatorProvider(DocumentMarshalerAggregatorConfig config){
        marshalFactory.setMarshallers(config.getMarshallers());
        marshalFactory.init(config.getTypes());
    }

    @Override
    public DocumentMarshalerAggregator get() {
        return marshalFactory;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.bot.document.spi;

import java.util.Arrays;
import java.util.List;
import ru.p03.classifier.model.DocTypeable;

/**
 *
 * @author altmf
 */
public class DocumentMarshalerAggregatorConfig {

    private List<? extends DocTypeable> types;
    private List<DocumentMarshaller> marshallers;

    public DocumentMarshalerAggregatorConfig(List<? extends DocTypeable> types,
            List<DocumentMarshaller> marshallers) {
        this.marshallers = marshallers;
        this.types = types;
    }
    
    public DocumentMarshalerAggregatorConfig(List<? extends DocTypeable> types,
            DocumentMarshaller... marshallers) {
        this.marshallers = Arrays.asList(marshallers);
        this.types = types;
    }

    /**
     * @return the types
     */
    public List<? extends DocTypeable> getTypes() {
        return types;
    }

    /**
     * @param types the types to set
     */
    public void setTypes(List<? extends DocTypeable> types) {
        this.types = types;
    }

    /**
     * @return the marshallers
     */
    public List<DocumentMarshaller> getMarshallers() {
        return marshallers;
    }

    /**
     * @param marshallers the marshallers to set
     */
    public void setMarshallers(List<DocumentMarshaller> marshallers) {
        this.marshallers = marshallers;
    }
}

/*
 */

package org.acme.mapping;

import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class ModelMapperBean {
    private ModelMapper modelMapper;

    @PostConstruct
    void init() {
        modelMapper = new ModelMapper();
    }

    @Produces
    public ModelMapper createModelMaper() {
        return modelMapper;
    }
}

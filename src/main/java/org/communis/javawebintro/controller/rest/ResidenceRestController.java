package org.communis.javawebintro.controller.rest;

import org.communis.javawebintro.dto.ResidenceWrapper;
import org.communis.javawebintro.exception.InvalidDataException;
import org.communis.javawebintro.exception.NotFoundException;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "residence")
public class ResidenceRestController {

    private final ResidenceService residenceService;

    @Autowired
    public ResidenceRestController(ResidenceService residenceService) {
        this.residenceService = residenceService;
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResidenceWrapper addResidence(@Valid ResidenceWrapper residenceWrapper) throws InvalidDataException, ServerException {
        return residenceService.addResidence(residenceWrapper);
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public ResidenceWrapper editResidence(@Valid ResidenceWrapper residenceWrapper)
            throws InvalidDataException, NotFoundException, ServerException {
       return residenceService.editResidence(residenceWrapper);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteResidence(@PathVariable("id") Long id) throws NotFoundException, ServerException{
        residenceService.deleteResidence(id);
    }
}

package org.communis.javawebintro.controller.rest;

import org.communis.javawebintro.dto.OccupantsWrapper;
import org.communis.javawebintro.entity.Occupants;
import org.communis.javawebintro.exception.InvalidDataException;
import org.communis.javawebintro.exception.NotFoundException;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.OccupantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "occupants")
public class OccupantsRestController {

    private final OccupantsService occupantsService;

    @Autowired
    public OccupantsRestController(OccupantsService occupantsService) {
        this.occupantsService = occupantsService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public OccupantsWrapper addOccupants(@Valid OccupantsWrapper occupantsWrapper) throws InvalidDataException, ServerException {
        return occupantsService.addOccupants(occupantsWrapper);
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public OccupantsWrapper editOccupants(@Valid OccupantsWrapper occupantsWrapper)
        throws InvalidDataException, NotFoundException, ServerException {
        return occupantsService.editOccupants(occupantsWrapper);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteOccupants(@PathVariable("id") Long id) throws NotFoundException, ServerException{
        occupantsService.deleteOccupants(id);
    }
}

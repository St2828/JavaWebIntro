package org.communis.javawebintro.controller.view;

import lombok.extern.log4j.Log4j2;
import org.communis.javawebintro.dto.OccupantsWrapper;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.dto.filters.ObjectFilter;
import org.communis.javawebintro.service.OccupantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Log4j2
@Controller
@RequestMapping(value = "occupants")
public class OccupantsController {

        private String OCCUPANTS_VIEWS_PATH = "occupants/";

        private final OccupantsService occupantsService;

        @Autowired
        public OccupantsController(OccupantsService occupantsService) {
            this.occupantsService = occupantsService;
        }

    @RequestMapping(value = "")
    public ModelAndView list(Pageable pageable, ObjectFilter filterResidenceWrapper) throws ServerException {
        ModelAndView usersPage = new ModelAndView(OCCUPANTS_VIEWS_PATH + "list");
        usersPage.addObject("filter", filterResidenceWrapper);
        usersPage.addObject("page", occupantsService.getPageByFilter(pageable, filterResidenceWrapper));
        return usersPage;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView addPage = new ModelAndView(OCCUPANTS_VIEWS_PATH + "add");
        addPage.addObject("occupants", new OccupantsWrapper());
        return addPage;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") Long id) throws ServerException {
        ModelAndView editPage = new ModelAndView(OCCUPANTS_VIEWS_PATH + "edit");
        editPage.addObject("occupants", occupantsService.getById(id));
        return editPage;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<OccupantsWrapper> getOccupants() throws ServerException {
        return occupantsService.getAllOccupants();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OccupantsWrapper getOccupantsById(@PathVariable("id") Long id) throws ServerException{
        return occupantsService.getById(id);
    }

    }

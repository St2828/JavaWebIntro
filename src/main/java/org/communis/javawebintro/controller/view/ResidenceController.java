package org.communis.javawebintro.controller.view;

import lombok.extern.log4j.Log4j2;
import org.communis.javawebintro.dto.ResidenceWrapper;
import org.communis.javawebintro.dto.filters.ResidenceFilterWrapper;
import org.communis.javawebintro.exception.NotFoundException;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.ResidenceService;
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
@RequestMapping(value = "residence")
public class ResidenceController {
    private String RESIDENCE_VIEWS_PATH = "residence/";

    private final ResidenceService residenceService;

    @Autowired
    public ResidenceController(ResidenceService residenceService) {
        this.residenceService = residenceService;
    }

    @RequestMapping(value = "")
    public ModelAndView list(Pageable pageable, ResidenceFilterWrapper filterResidenceWrapper) throws ServerException {
        ModelAndView usersPage = new ModelAndView(RESIDENCE_VIEWS_PATH + "list");
        usersPage.addObject("filter", filterResidenceWrapper);
        usersPage.addObject("page", residenceService.getPageByFilter(pageable, filterResidenceWrapper));
        return usersPage;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView addPage = new ModelAndView(RESIDENCE_VIEWS_PATH + "add");
        addPage.addObject("residence", new ResidenceWrapper());
        return addPage;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") Long id) throws ServerException {
        ModelAndView editPage = new ModelAndView(RESIDENCE_VIEWS_PATH + "edit");
        editPage.addObject("residence", residenceService.getById(id));
        return editPage;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ResidenceWrapper> getResidence() throws ServerException {
        return residenceService.getAllResidence();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResidenceWrapper getResidenceById(@PathVariable("id") Long id) throws ServerException{
        return residenceService.getById(id);
    }


}

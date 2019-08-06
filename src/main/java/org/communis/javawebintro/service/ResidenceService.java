package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.filters.ResidenceFilterWrapper;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.repository.ResidenceRepository;
import org.communis.javawebintro.repository.specifications.ResidenceSpecification;
import org.communis.javawebintro.dto.ResidenceWrapper;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformation;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = ServerException.class)
public class ResidenceService {

    private final ResidenceRepository residenceRepository;

    @Autowired
    public ResidenceService(ResidenceRepository residenceRepository){
        this.residenceRepository = residenceRepository;}


    /**
     * Метод поиска и получения всех жилищ
     * @return список экземпляров класса ResidenceWrapper
     * @throws ServerException генерирует исключение с кодом RESIDENCE_LIST_ERROR
     */
    public List<ResidenceWrapper> getAllResidence() throws ServerException{
        try {
            return residenceRepository.findAll().stream().map(ResidenceWrapper::new).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.RESIDENCE_LIST_ERROR), ex);
        }
    }

    /**
     * Получает из базы страницу объектов {@link ResidenceWrapper} в зависимости от информации о пагинаторе и параметрах фильтра
     *
     * @param filter   информация о фильтре
     * @param pageable информация о пагинаторе
     * @return страница объектов
     */
    public Page getPageByFilter(Pageable pageable, ResidenceFilterWrapper filter) throws ServerException {
        try {
            return residenceRepository.findAll(ResidenceSpecification.build(filter), pageable)
                    .map(ResidenceWrapper::new);
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.RESIDENCE_LIST_ERROR), ex);
        }
    }



}

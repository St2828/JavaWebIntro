package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.filters.ResidenceFilterWrapper;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.repository.ResidenceRepository;
import org.communis.javawebintro.entity.Residence;
import org.communis.javawebintro.repository.specifications.ResidenceSpecification;
import org.communis.javawebintro.dto.ResidenceWrapper;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
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

    /**
     * Метод добавления нового жилища в базу данных
     * @param residenceWrapper содержит данные жилища
     * @return true - при успешном добавлении
     * @throws ServerException генерирует исключение
     */
    public String addResidence(ResidenceWrapper residenceWrapper) throws ServerException{
        try{
                Residence residence = new Residence();
                residenceWrapper.fromWrapper(residence);
                residenceRepository.save(residence);
                return "true";
        }catch (Exception ex){
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.RESIDENCE_ADD_ERROR), ex);
        }
    }

    /**
     * Метод обновления данных существующего жилища
     * @param residenceWrapper содержит обновленные данные
     * @return true - при успешном обновлении данных
     * @throws ServerException генерирует исключение
     */
    public String editResidence(ResidenceWrapper residenceWrapper) throws ServerException{
        try{
            Residence residence = getResidence(residenceWrapper.getId());
            residenceWrapper.fromWrapper(residence);
            residenceRepository.save(residence);
            return "true";
        }catch (Exception ex){
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.RESIDENCE_UPDATE_ERROR), ex);
        }
    }

    /**
     * Метод удаления существующего жилища из базы данных
     * @param residenceWrapper содержит идентификатор удаляемой записи
     * @return true - при успешном удалении
     * @throws ServerException генерирует исключение
     */
    public String deleteResidence(ResidenceWrapper residenceWrapper) throws ServerException{
        try{
            residenceRepository.delete(residenceWrapper.getId());
            return "true";
        }catch (Exception ex){
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.RESIDENCE_DELETE_ERROR), ex);
        }
    }

    /**
     * Получает информацию о жилище по идентификатору из базы
     *
     * @param id идентификатор жилища
     * @return информация о жилище
     */
    public ResidenceWrapper getForEdit(Long id) throws ServerException {
        try {
            return new ResidenceWrapper(residenceRepository.findOne(id));
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.RESIDENCE_INFO_ERROR), ex);
        }
    }



    private Residence getResidence(Long id) throws ServerException {
        return residenceRepository.findById(id)
                .orElseThrow(() -> new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_NOT_FOUND)));
    }

}

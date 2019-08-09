package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.filters.ObjectFilter;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.repository.OccupantsRepository;
import org.communis.javawebintro.entity.Occupants;
import org.communis.javawebintro.repository.specifications.OccupantsSpecification;
import org.communis.javawebintro.dto.OccupantsWrapper;
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
public class OccupantsService {


    private final OccupantsRepository occupantsRepository;

    @Autowired
    public OccupantsService(OccupantsRepository occupantsRepository){
        this.occupantsRepository = occupantsRepository;
    }


    /**
     * Метод поиска и получения всех жителей
     * @return список экземпляров класса OccupantsWrapper
     * @throws ServerException генерирует исключение
     */
    public List<OccupantsWrapper> getAllOccupants() throws ServerException{
        try {
            return occupantsRepository.findAll().stream().map(OccupantsWrapper::new).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.OCCUPANTS_LIST_ERROR), ex);
        }
    }

    /**
     * Получает из базы страницу объектов {@link OccupantsWrapper} в зависимости от информации о пагинаторе и параметрах фильтра
     * @param filter   информация о фильтре
     * @param pageable информация о пагинаторе
     * @return страница объектов
     */
    public Page getPageByFilter(Pageable pageable, ObjectFilter filter) throws ServerException {
        try {
            return occupantsRepository.findAll(OccupantsSpecification.build(filter), pageable)
                    .map(OccupantsWrapper::new);
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.OCCUPANTS_LIST_ERROR), ex);
        }
    }

    /**
     * Метод добавления нового жителя в базу данных
     * @param occupantsWrapper содержит данные жителя
     * @return true - при успешном добавлении
     * @throws ServerException генерирует исключение
     */
    public OccupantsWrapper addOccupants(OccupantsWrapper occupantsWrapper) throws ServerException{
        try{
            Occupants occupants = new Occupants();
            occupantsWrapper.fromWrapper(occupants);
            return new OccupantsWrapper(occupantsRepository.save(occupants));
        }catch (Exception ex){
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.OCCUPANTS_ADD_ERROR), ex);
        }
    }


    /**
     * Метод удаления существующего жителя из базы данных
     * @throws ServerException генерирует исключение
     */
    public void deleteOccupants(Long id) throws ServerException{
        try{
            Occupants occupants = getOccupants(id);
            occupantsRepository.delete(occupants);
        }catch (Exception ex){
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.RESIDENCE_DELETE_ERROR), ex);
        }
    }


    /**
     * Получает информацию о жителе с заданным идентификатором из базы и пребразует ее в объект класса {@link OccupantsWrapper}
     *
     * @param id идентификатор жителя
     * @return объект, содержащий информацию о жителе
     */
    public OccupantsWrapper getById(Long id) throws ServerException {
        try {
            return new OccupantsWrapper(getOccupants(id));
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_INFO_ERROR), ex);
        }
    }

    /**
     * Метод обновления данных существующего жителя
     * @param occupantsWrapper содержит обновленные данные
     * @return true - при успешном обновлении данных
     * @throws ServerException генерирует исключение
     */
    public OccupantsWrapper editOccupants(OccupantsWrapper occupantsWrapper) throws ServerException{
        try{
            Occupants occupants = getOccupants(occupantsWrapper.getId());
            occupantsWrapper.fromWrapper(occupants);
            return new OccupantsWrapper(occupantsRepository.save(occupants));
        }catch (Exception ex){
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.OCCUPANTS_UPDATE_ERROR), ex);
        }
    }

    private Occupants getOccupants(Long id) throws ServerException {
        return occupantsRepository.findById(id)
                .orElseThrow(() -> new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_NOT_FOUND)));
    }


}

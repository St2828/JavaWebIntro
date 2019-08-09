package org.communis.javawebintro.service;

import org.communis.javawebintro.dto.filters.ResidenceFilterWrapper;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.repository.ResidenceRepository;
import org.communis.javawebintro.repository.UserRepository;
import org.communis.javawebintro.entity.User;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = ServerException.class)
public class ResidenceService {

    private final ResidenceRepository residenceRepository;
    private final UserRepository userRepository;

    @Autowired
    public ResidenceService(ResidenceRepository residenceRepository, UserRepository userRepository){
        this.residenceRepository = residenceRepository;
        this.userRepository = userRepository;
       }


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
    public ResidenceWrapper addResidence(ResidenceWrapper residenceWrapper) throws ServerException{
        try{
                Residence residence = new Residence();
                residenceWrapper.fromWrapper(residence);
                return new ResidenceWrapper(residenceRepository.save(residence));


            /*   if (residenceWrapper.getUser()!=null){
             *   Optional<User> user = userRepository.findOne(residenceWrapper.getUser().getId());
             *      user.ifPresent(residence::setUser);
             *    }
             */


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
    public ResidenceWrapper editResidence(ResidenceWrapper residenceWrapper) throws ServerException{
        try{
            Residence residence = getResidence(residenceWrapper.getId());
            residenceWrapper.fromWrapper(residence);
            return new ResidenceWrapper(residenceRepository.save(residence));
        }catch (Exception ex){
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.RESIDENCE_UPDATE_ERROR), ex);
        }
    }

    /**
     * Метод удаления существующего жилища из базы данных
     * @throws ServerException генерирует исключение
     */

    public void deleteResidence(Long id) throws ServerException{
        try{
            Residence residence = getResidence(id);
            residenceRepository.delete(residence);
        }catch (Exception ex){
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.RESIDENCE_DELETE_ERROR), ex);
        }
    }

    /**
     * Получает информацию о жилище с заданным идентификатором из базы и пребразует ее в объект класса {@link ResidenceWrapper}
     *
     * @param id идентификатор жилища
     * @return объект, содержащий информацию о жилище
     */
    public ResidenceWrapper getById(Long id) throws ServerException {
        try {
            return new ResidenceWrapper(getResidence(id));
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.USER_INFO_ERROR), ex);
        }
    }


    private Residence getResidence(Long id) throws ServerException {
        return residenceRepository.findById(id)
                .orElseThrow(() -> new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.DATA_NOT_FOUND)));
    }

}

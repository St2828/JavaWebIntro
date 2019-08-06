package org.communis.javawebintro.service;

import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.repository.ResidenceRepository;
import org.communis.javawebintro.dto.ResidenceWrapper;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.exception.error.ErrorCodeConstants;
import org.communis.javawebintro.exception.error.ErrorInformation;
import org.communis.javawebintro.exception.error.ErrorInformationBuilder;

import org.springframework.beans.factory.annotation.Autowired;
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
     * Метод поиска и получения всех тренировочных программ
     * @return список экземпляров класса TrainingProgramWrapper (список тренировочных программ)
     * @throws ServerException генерирует исключение с кодом TRAINING_PROGRAM_LIST_ERROR
     */
    public List<ResidenceWrapper> getAllResidence() throws ServerException{
        try {
            return residenceRepository.findAll().stream().map(ResidenceWrapper::new).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ServerException(ErrorInformationBuilder.build(ErrorCodeConstants.RESIDENCE_LIST_ERROR), ex);
        }
    }
}

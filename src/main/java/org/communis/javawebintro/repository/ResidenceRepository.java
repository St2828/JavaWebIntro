package org.communis.javawebintro.repository;

import org.communis.javawebintro.entity.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.List;

public interface ResidenceRepository extends JpaRepository<Residence, Long>, JpaSpecificationExecutor<Residence>{

    Optional<Residence> findById(Long id);

    List<Residence> findAllByName(String name);
}

package org.communis.javawebintro.repository;

import org.communis.javawebintro.entity.Residence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResidenceRepository extends JpaRepository<Residence, Long> {

    Optional<Residence> findById(Long id);

}

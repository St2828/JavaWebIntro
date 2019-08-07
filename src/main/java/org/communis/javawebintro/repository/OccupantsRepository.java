package org.communis.javawebintro.repository;

import org.communis.javawebintro.entity.Occupants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.List;

public interface OccupantsRepository extends JpaRepository<Occupants, Long>, JpaSpecificationExecutor<Occupants> {

    Optional<Occupants> findById(Long id);

    List<Occupants> findAllByName(String name);

}

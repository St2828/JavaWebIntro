package org.communis.javawebintro.repository;

import org.communis.javawebintro.entity.Occupants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface OccupantsRepository extends JpaRepository<Occupants, Long> {

    Optional<Occupants> findById(Long id);

    List<Occupants> findAllByName(String name);

}

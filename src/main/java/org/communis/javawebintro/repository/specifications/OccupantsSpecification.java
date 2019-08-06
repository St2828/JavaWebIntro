package org.communis.javawebintro.repository.specifications;

import org.communis.javawebintro.dto.filters.ObjectFilter;
import org.communis.javawebintro.entity.Occupants;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class OccupantsSpecification implements Specification<Occupants>
{

    private OccupantsSpecification() {

    }

    public static OccupantsSpecification build(final ObjectFilter filter) {
        return new OccupantsSpecification() {
            @Override
            public Predicate toPredicate(Root<Occupants> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                final List predicates = new ArrayList();

                if (filter != null) {
                    if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
                        predicates.add(cb.or(
                                cb.like(cb.upper(root.get("surname")), '%' + filter.getSearch().toUpperCase() + '%'),
                                cb.like(cb.upper(root.get("profession")), '%' + filter.getSearch().toUpperCase() + '%'),
                                cb.like(cb.upper(root.get("age")), '%' + filter.getSearch().toUpperCase() + '%'),
                                cb.like(cb.upper(root.get("name")), '%' + filter.getSearch().toUpperCase() + '%')));

                    }

                }
                return cb.and((Predicate[]) predicates.toArray(new Predicate[0]));
            }
        };
    }
}

package org.communis.javawebintro.repository.specifications;

import org.communis.javawebintro.dto.filters.ResidenceFilterWrapper;
import org.communis.javawebintro.entity.Residence;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class ResidenceSpecification implements Specification<Residence>
{

    private ResidenceSpecification() {

    }

    public static ResidenceSpecification build(final ResidenceFilterWrapper filter) {
        return new ResidenceSpecification() {
            @Override
            public Predicate toPredicate(Root<Residence> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                final List predicates = new ArrayList();

                if (filter != null) {
                    if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
                        predicates.add(cb.or(
                                cb.like(cb.upper(root.get("street")), '%' + filter.getSearch().toUpperCase() + '%'),
                                cb.like(cb.upper(root.get("adress")), '%' + filter.getSearch().toUpperCase() + '%'),
                                cb.like(cb.upper(root.get("flat")), '%' + filter.getSearch().toUpperCase() + '%'),
                                cb.like(cb.upper(root.get("zip")), '%' + filter.getSearch().toUpperCase() + '%')));
                    }
                    if (filter.getUser() != null) {
                        predicates.add(cb.equal(root.get("status"), filter.getUser()));
                    }
                }
                return cb.and((Predicate[]) predicates.toArray(new Predicate[0]));
            }
        };
    }

}

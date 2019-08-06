package org.communis.javawebintro.dto.filters;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResidenceFilterWrapper extends ObjectFilter{

    private Long user;
}

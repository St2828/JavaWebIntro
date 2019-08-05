package org.communis.javawebintro.dto;

import lombok.Data;
import org.communis.javawebintro.entity.Occupants;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class OccupantsWrapper implements ObjectWrapper<Occupants>, Serializable
{
    private Long id;

    @NotNull
    @Size(max = 100)
    private String surname;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String secondName;

    @NotNull
    @Size(max = 100)
    private String profession;

    @Size(max = 10)
    private Integer age;

    private ResidenceWrapper residence;

    public OccupantsWrapper() {    }

    public OccupantsWrapper(Occupants occupants)
    {
        toWrapper(occupants);
    }

    @Override
    public void toWrapper(Occupants item)
    {
        if(item!=null)
        {
            id = item.getId();
            surname = item.getSurname();
            name = item.getName();
            secondName = item.getSecondName();
            profession = item.getProfession();
            age = item.getAge();
            residence = new ResidenceWrapper(item.getResidence());
        }
    }

    @Override
    public void fromWrapper(Occupants item) {
        if(item!=null) {
            item.setSurname(surname);
            item.setName(name);
            item.setSecondName(secondName);
            item.setProfession(profession);
            item.setAge(age);
        }
    }
}

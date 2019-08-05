package org.communis.javawebintro.dto;

import lombok.Data;
import org.communis.javawebintro.entity.Residence;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class ResidenceWrapper implements ObjectWrapper<Residence>, Serializable
{
    private Long id;

    @NotNull
    @Size(max = 100)
    private String street;

    @NotNull
    @Size(max = 20)
    private String adress;

    @Size(max = 20)
    private String flat;

    @NotNull
    @Size(max = 20)
    private String zip;

    private UserWrapper user;

    public ResidenceWrapper() {    }

    public ResidenceWrapper(Residence residence)
    {
        toWrapper(residence);
    }

    @Override
    public void toWrapper(Residence item)
    {
        if(item!=null)
        {
            id = item.getId();
            street = item.getStreet();
            adress = item.getAdress();
            flat = item.getFlat();
            zip = item.getZip();
            user = new UserWrapper(item.getUser());
        }
    }

    @Override
    public void fromWrapper(Residence item) {
        if(item!=null) {
            item.setStreet(street);
            item.setAdress(adress);
            item.setFlat(flat);
            item.setZip(zip);
        }
    }
}

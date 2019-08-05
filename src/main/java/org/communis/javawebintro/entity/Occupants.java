package org.communis.javawebintro.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Optional;

@Data
@Entity
@Table(name = "occupants")
public class Occupants {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "profession")
    private String profession;

    @Column(name = "age")
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_residence")
    private Residence residence;

}

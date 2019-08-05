package org.communis.javawebintro.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Optional;

@Data
@Entity
@Table(name = "residence")
public class Residence {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "adress")
    private String adress;

    @Column(name = "flat")
    private String flat;

    @Column(name = "zip")
    private String zip;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_users")
    private User user;

}

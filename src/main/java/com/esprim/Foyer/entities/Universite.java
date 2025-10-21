package com.esprim.Foyer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Universite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUniversite;

    private String nomUniversite;
    private String adresse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "foyer_id", referencedColumnName = "idFoyer")
    private Foyer foyer;
}
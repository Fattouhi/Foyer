package com.esprim.Foyer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Setter
@Getter
@Entity
public class Bloc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBloc;

    private String nomBloc;
    private Long capaciteBloc;

    @ManyToOne
    @JoinColumn(name = "foyer_id", referencedColumnName = "idFoyer")
    private Foyer foyer;

    @OneToMany(mappedBy = "bloc", cascade = CascadeType.ALL)
    private Set<Chambre> chambres;
}
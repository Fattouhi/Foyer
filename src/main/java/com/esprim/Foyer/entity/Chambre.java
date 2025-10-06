package com.esprim.Foyer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Setter
@Getter
@Entity
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChambre;

    private Long numeroChambre;

    @Enumerated(EnumType.STRING)
    private TypeChambre typeChambre;

    @ManyToOne
    @JoinColumn(name = "bloc_id", referencedColumnName = "idBloc")
    private Bloc bloc;

    @OneToMany(mappedBy = "chambre", cascade = CascadeType.ALL)
    private Set<Reservation> reservations;
}
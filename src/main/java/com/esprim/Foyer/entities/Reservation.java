package com.esprim.Foyer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
public class Reservation {
    @Id
    private String idReservation;

    @Temporal(TemporalType.DATE)
    private Date anneeUniversitaire;

    private Boolean estValide;

    @ManyToOne
    @JoinColumn(name = "chambre_id", referencedColumnName = "idChambre")
    private Chambre chambre;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "reservation_etudiant",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "etudiant_id")
    )
    private Set<Etudiant> etudiants;
}

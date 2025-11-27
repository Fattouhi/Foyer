package com.esprim.Foyer.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@IdClass(ReservationId.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {
    @Id
    private String idReservation;

    @Temporal(TemporalType.DATE)
    private Date anneeUniversitaire;

    private boolean estValide;

    @ManyToMany
    @JoinTable(
            name = "reservation_etudiant",
            joinColumns = {
                    @JoinColumn(name = "idReservation", referencedColumnName = "idReservation")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "idEtudiant", referencedColumnName = "idEtudiant")
            }
    )
    private Set<Etudiant> etudiants;

    @OneToOne(mappedBy = "reservation")
    private Chambre chambre;
}
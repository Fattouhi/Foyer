package com.esprim.Foyer.repositories;

import com.esprim.Foyer.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    // JPQL: Get reservations by year and university name
    @Query("SELECT r FROM Reservation r " +
            "JOIN r.chambre c " +
            "JOIN c.bloc b " +
            "JOIN b.foyer f " +
            "JOIN f.universite u " +
            "WHERE YEAR(r.anneeUniversitaire) = YEAR(:anneeUniversite) " +
            "AND u.nomUniversite = :nomUniversite")
    List<Reservation> findReservationsByAnneeAndUniversite(
            @Param("anneeUniversite") Date anneeUniversite,
            @Param("nomUniversite") String nomUniversite);
}
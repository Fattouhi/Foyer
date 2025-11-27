package com.esprim.Foyer.repositories;

import com.esprim.Foyer.entities.Chambre;
import com.esprim.Foyer.entities.TypeChambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IChambreRepository extends JpaRepository<Chambre, Long> {

    // JPQL: Get chambres by university name
    @Query("SELECT c FROM Chambre c " +
            "JOIN c.bloc b " +
            "JOIN b.foyer f " +
            "JOIN f.universite u " +
            "WHERE u.nomUniversite = :nomUniversite")
    List<Chambre> findChambresParNomUniversite(@Param("nomUniversite") String nomUniversite);

    // JPQL: Get chambres by bloc and type
    @Query("SELECT c FROM Chambre c " +
            "WHERE c.bloc.idBloc = :idBloc AND c.typeC = :type")
    List<Chambre> findChambresParBlocEtTypeJPQL(
            @Param("idBloc") Long idBloc,
            @Param("type") TypeChambre type);

    // Keywords: Alternative solution for getting chambres by bloc and type
    List<Chambre> findByBlocIdBlocAndTypeC(Long idBloc, TypeChambre typeC);

    // JPQL: Get non-reserved chambres by university and type
    @Query("SELECT c FROM Chambre c " +
            "WHERE c.bloc.foyer.universite.nomUniversite = :nomUniversite " +
            "AND c.typeC = :type " +
            "AND (c.reservation IS NULL OR c.reservation.estValide = false)")
    List<Chambre> findChambresNonReserveesParNomUnivEtType(
            @Param("nomUniversite") String nomUniversite,
            @Param("type") TypeChambre type);

    // JPQL: Get all non-reserved chambres for scheduler
    @Query("SELECT c FROM Chambre c " +
            "WHERE c.reservation IS NULL OR c.reservation.estValide = false")
    List<Chambre> findChambresNonReservees();
}
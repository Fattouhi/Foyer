package com.esprim.Foyer.services;

import com.esprim.Foyer.entities.Bloc;
import java.util.List;

public interface IBlocService {
    List<Bloc> retrieveBlocs();
    Bloc updateBloc(Bloc bloc);
    Bloc addBloc(Bloc bloc);
    Bloc retrieveBloc(long idBloc);
    void removeBloc(long idBloc);
    Bloc affecterChambresABloc(List<Long> numChambre, long idBloc);
}
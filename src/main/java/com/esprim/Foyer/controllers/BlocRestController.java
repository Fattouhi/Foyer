package com.esprim.Foyer.controllers;

import com.esprim.Foyer.entities.Bloc;
import com.esprim.Foyer.services.IBlocService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bloc")
public class BlocRestController {

    private final IBlocService blocService;

    @GetMapping("/retrieve-all-blocs")
    public List<Bloc> retrieveBlocs() {
        return blocService.retrieveBlocs();
    }

    @GetMapping("/retrieve-bloc/{idBloc}")
    public Bloc retrieveBloc(@PathVariable long idBloc) {
        return blocService.retrieveBloc(idBloc);
    }

    @PostMapping("/add-bloc")
    public Bloc addBloc(@RequestBody Bloc bloc) {
        return blocService.addBloc(bloc);
    }

    @PutMapping("/update-bloc")
    public Bloc updateBloc(@RequestBody Bloc bloc) {
        return blocService.updateBloc(bloc);
    }

    @DeleteMapping("/remove-bloc/{idBloc}")
    public void removeBloc(@PathVariable long idBloc) {
        blocService.removeBloc(idBloc);
    }

    @PutMapping("/affecter-chambres/{idBloc}")
    public Bloc affecterChambresABloc(@RequestBody List<Long> chambresIds,
                                      @PathVariable long idBloc) {
        return blocService.affecterChambresABloc(chambresIds, idBloc);
    }
}

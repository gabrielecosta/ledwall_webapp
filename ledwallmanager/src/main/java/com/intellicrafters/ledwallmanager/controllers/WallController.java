package com.intellicrafters.ledwallmanager.controllers;

import com.intellicrafters.ledwallmanager.entities.Impianto;
import com.intellicrafters.ledwallmanager.repositories.ImpiantoRepository;
import com.intellicrafters.ledwallmanager.repositories.PalinsestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path="/wall")
public class WallController {

    @Autowired
    private PalinsestoRepository palinsestoRepository;

    @Autowired
    private ImpiantoRepository impiantoRepository;

    @GetMapping
    public String wall(@RequestParam(value = "id", required = false, defaultValue = "1") Integer id, Model model) {
        System.out.println("Requested wall with ID: " + id);

        Impianto impianto = impiantoRepository.findByIdImpianto(id);

        if (impianto == null) {
            System.out.println("L'impianto non esiste!");
            return "wall_error";
        }

        // Inizializza lo stato dell'impianto
        int statoImpianto = impianto.getStatusImpianto();

        // Se l'impianto è disabilitato restituisci un messaggio di impianto disattivato
        if (statoImpianto == 0) {
            System.out.println("L'impianto è disattivato!");
            return "wall_disabled";
        }

        // In caso contrario ottieni il path del palinsesto e restituisci il wall
        String pathPalinsesto = impianto.getPalinsesto().getPathPalinsesto();

        model.addAttribute("path_palinsesto", pathPalinsesto);
        model.addAttribute("id_impianto", id);

        return "wall";

    }

}
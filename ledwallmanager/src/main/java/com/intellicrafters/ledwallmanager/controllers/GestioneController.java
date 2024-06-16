package com.intellicrafters.ledwallmanager.controllers;

import com.intellicrafters.ledwallmanager.entities.Impianto;
import com.intellicrafters.ledwallmanager.entities.Palinsesto;
import com.intellicrafters.ledwallmanager.repositories.ImpiantoRepository;
import com.intellicrafters.ledwallmanager.repositories.PalinsestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gestioneImpianti")
public class GestioneController {

    @Autowired
    private ImpiantoRepository impiantoRepository;

    @Autowired
    private PalinsestoRepository palinsestoRepository;

    @GetMapping(path="/form")
    public String formImpianti(Model model) {

        // Creazione di un nuovo impianto
        Impianto impianto = new Impianto();

        // Creazione di un nuovo palinsesto
        Palinsesto palinsesto = new Palinsesto();

        // Impostazione dell'ID dell'impianto
        impianto.setIdImpianto(0);

        // Impostazione della descrizione dell'impianto
        impianto.setDescrizione("new desc");

        // Impostazione della latitudine e della longitudine
        impianto.setLatitudine(0.0);
        impianto.setLongitudine(0.0);

        // Impostazione dello stato dell'impianto
        impianto.setStatusImpianto(0);

        // Creazione di un nuovo palinsesto
        palinsesto.setIdPalinsesto("P1");
        palinsesto.setPathPalinsesto("../path1");

        // Associazione del palinsesto
        impianto.setPalinsesto(palinsesto);

        // Ottengo la lista dei palinsesti
        List<Palinsesto> listaPalinsesti = palinsestoRepository.findAll();

        // Aggiungo al modello
        model.addAttribute("lista_palinsesti", listaPalinsesti);
        model.addAttribute("impianto", impianto);

        return "form_impianto";

    }

    @GetMapping(path="/")
    public String getAllImpianti(Model model) {

        // Lista degli impianti
        Iterable<Impianto> listImpianti = impiantoRepository.findAll();

        // Aggiunta al modello
        model.addAttribute("listImpianti", listImpianti);

        return "gestione_impianti";
    }

    @GetMapping("/getImpianto")
    public String getImpianto(Model model, @RequestParam("id") int idImpianto) {

        // Ottengo l'impianto
        Impianto impianto = impiantoRepository.findByIdImpianto(idImpianto);

        // Ottengo la lista dei palinsesti
        List<Palinsesto> listaPalinsesti = palinsestoRepository.findAll();

        // Aggiunta al modello
        model.addAttribute("impianto", impianto);
        model.addAttribute("lista_palinsesti", listaPalinsesti);
        return "pannello_impianto";

    }

    @PostMapping("/updateImpianto")
    public String updateImpianto(@ModelAttribute Impianto impianto, Model model) {

        try {
            System.out.println("Aggiornamento dell'impianto: " + impianto.getIdImpianto());
            impiantoRepository.save(impianto);
            return "redirect:/gestioneImpianti/";
        }
        catch (Exception e) {
            System.out.println(e);
            return "error";
        }
    }


    @PostMapping("/aggiungiImpianto")
    public String aggiungiImpianto(@ModelAttribute Impianto impianto) {
        try {
            System.out.println("Aggiunta dell'impianto: " + impianto.getDescrizione());
            impiantoRepository.save(impianto);

            return "redirect:/gestioneImpianti/";

        } catch (Exception e) {
            System.out.println(e);
            return "error";
        }
    }


}

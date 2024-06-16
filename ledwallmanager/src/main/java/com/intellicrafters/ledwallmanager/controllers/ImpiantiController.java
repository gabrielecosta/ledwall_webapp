package com.intellicrafters.ledwallmanager.controllers;

import com.intellicrafters.ledwallmanager.entities.Impianto;
import com.intellicrafters.ledwallmanager.repositories.ImpiantoRepository;
import com.intellicrafters.ledwallmanager.services.PalinsestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ledwall")
public class ImpiantiController {

    @Autowired
    private ImpiantoRepository impiantorepo;

    private PalinsestoService palinsestoService;

    @GetMapping(path="/impianti")
    public @ResponseBody Iterable<Impianto> getAllImpianti() {
        // This returns a JSON or XML with the users
        return impiantorepo.findAll();
    }

   /* @GetMapping("/id={impiantoId}")
    public String getPalinsestoNomeByImpiantoIdNative(Model model,@PathVariable Long impiantoId) {
        // String path_palinsesto = impiantoService.getPalinsestoNomeByImpiantoIdNative(impiantoId);
        String path_palinsesto = impiantorepo.findPathPalinsestoByImpiantoIdNative(impiantoId);
        // int status = impiantoService.findStatusImpianto(impiantoId);
        boolean status_b = impiantorepo.findStatusImpianto(impiantoId);
        int status = 0;
        if (status_b == true) {
            status = 1;
        }
        model.addAttribute("path_palinsesto", path_palinsesto);
        model.addAttribute("impianto_id", impiantoId);
        model.addAttribute("status_impianto", status);
        return "led_wall";
    }*/
}

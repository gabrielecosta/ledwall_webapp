package com.intellicrafters.ledwallmanager.controllers;

import com.intellicrafters.ledwallmanager.entities.Segnalazione;
import com.intellicrafters.ledwallmanager.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/report")
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping
    public String getReportPage(
            @RequestParam(required = false, defaultValue = "") Integer idImpianto,
            @RequestParam(required = false, defaultValue = "") String idPalinsesto,
            @RequestParam(required = false, defaultValue = "") String idCartellone,
            Model model) {

        Iterable<Segnalazione> segnalazioni;

        if (idImpianto != null && !idPalinsesto.isEmpty() && !idCartellone.isEmpty()) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdPalinsestoAndIdCartellone(idImpianto, idPalinsesto, idCartellone);
        } else if (idImpianto != null && !idPalinsesto.isEmpty()) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdPalinsesto(idImpianto, idPalinsesto);
        } else if (idImpianto != null && !idCartellone.isEmpty()) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdCartellone(idImpianto, idCartellone);
        } else if (idImpianto != null) {
            segnalazioni = reportRepository.findByIdImpianto(idImpianto);
        } else if (!idPalinsesto.isEmpty() && !idCartellone.isEmpty()) {
            segnalazioni = reportRepository.findByIdPalinsestoAndIdCartellone(idPalinsesto, idCartellone);
        } else if (!idPalinsesto.isEmpty()) {
            segnalazioni = reportRepository.findByIdPalinsesto(idPalinsesto);
        } else if (!idCartellone.isEmpty()) {
            segnalazioni = reportRepository.findByIdCartellone(idCartellone);
        } else {
            segnalazioni = reportRepository.findAll();
        }

        System.out.println(segnalazioni);

        model.addAttribute("segnalazioni", segnalazioni);
        return "report";
    }

    @GetMapping(path = "/get_cartelloni")
    public @ResponseBody ArrayList<String> getCartelloni(Model model) {
        // Ottengo le segnalazioni
        Iterable<Segnalazione> segnalazioni = reportRepository.findAll();

        ArrayList<String> cartelloni = new ArrayList<>();

        for (Segnalazione s : segnalazioni) {
            if (!cartelloni.contains(s.getIdCartellone())) {
                cartelloni.add(s.getIdCartellone());
            }
        }

        Collections.sort(cartelloni, (s1, s2) -> {
            // Estrai i numeri dalle stringhe
            int num1 = Integer.parseInt(s1.substring(1));
            int num2 = Integer.parseInt(s2.substring(1));
            // Confronta i numeri
            return Integer.compare(num1, num2);
        });

        System.out.println(cartelloni);

        model.addAttribute("cartelloni", cartelloni);

        return cartelloni;

    }

    @GetMapping(path="/get_tempo_impressioni")
    public @ResponseBody HashMap<String, Double> getTempoImpressioni(@RequestParam(required = false) Integer id_impianto, @RequestParam(required = false) String id_palinsesto, @RequestParam(required = false) String id_cartellone, @RequestParam Date from_date, @RequestParam Date to_date, Model model) {

        // Ottengo le segnalazioni in base alla modalità di filtraggio opportuna
        Iterable<Segnalazione> segnalazioni;

        if (id_impianto != null && id_palinsesto != null && id_cartellone != null) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdPalinsestoAndIdCartellone(id_impianto, id_palinsesto, id_cartellone);
        }
        else if (id_impianto != null && id_palinsesto != null) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdPalinsesto(id_impianto, id_palinsesto);
        }
        else if (id_impianto != null && id_cartellone != null) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdCartellone(id_impianto, id_cartellone);
        }
        else if (id_impianto != null) {
            segnalazioni = reportRepository.findByIdImpianto(id_impianto);
        }
        else if (id_palinsesto != null && id_cartellone != null) {
            segnalazioni = reportRepository.findByIdPalinsestoAndIdCartellone(id_palinsesto, id_cartellone);
        }
        else if (id_palinsesto != null) {
            segnalazioni = reportRepository.findByIdPalinsesto(id_palinsesto);
        }
        else if (id_cartellone != null) {
            segnalazioni = reportRepository.findByIdCartellone(id_cartellone);
        }
        else {
            segnalazioni = reportRepository.findAll();
        }

        // Creo una HashMap contenente gli ID dei cartelloni e le impressioni di ciascuno di esso
        HashMap<String, Double> tempoImpressioniCartelloni = new HashMap<>();

        // Itera tutte le segnalazioni
        for (Segnalazione s : segnalazioni) {

            System.out.println(s.getTimestamp());

            if (s.getTimestamp().equals(from_date) || (s.getTimestamp().after(from_date)) && (s.getTimestamp().equals(to_date) || s.getTimestamp().before(to_date))) {
                // Se l'ID cartellone è già contenuto nell'HashMap, incrementa il numero di impressioni, altrimenti aggiungila all'HashMap
                if (tempoImpressioniCartelloni.containsKey(s.getIdCartellone())) {
                    tempoImpressioniCartelloni.replace(s.getIdCartellone(), tempoImpressioniCartelloni.get(s.getIdCartellone()) + s.getDurata());
                }
                else {
                    tempoImpressioniCartelloni.put(s.getIdCartellone(), new Double(s.getDurata()));
                }
            }

        }

        // Conversione in ore
        for (Map.Entry<String, Double> entry : tempoImpressioniCartelloni.entrySet()) {
            String entryKey = entry.getKey();
            Double entryValue = entry.getValue();

            tempoImpressioniCartelloni.replace(entryKey, Math.floor((entryValue / 60) * 100) / 100);
        }

        model.addAttribute("tempo_impressioni_cartelloni", tempoImpressioniCartelloni);

        // Stampo la HashMap
        System.out.println(tempoImpressioniCartelloni);

        // Ritorna l'HashMap
        return tempoImpressioniCartelloni;

    }

    @GetMapping(path = "/get_palinsesti_path")
    public @ResponseBody ArrayList<String> getPalinsestiPath(@RequestParam(required = false) Integer id_impianto, @RequestParam(required = false) String id_palinsesto, @RequestParam(required = false) String id_cartellone, Model model) {
        // Ottengo le segnalazioni in base alla modalità di filtraggio opportuna
        Iterable<Segnalazione> segnalazioni;

        if (id_impianto != null && id_palinsesto != null && id_cartellone != null) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdPalinsestoAndIdCartellone(id_impianto, id_palinsesto, id_cartellone);
        } else if (id_impianto != null && id_palinsesto != null) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdPalinsesto(id_impianto, id_palinsesto);
        } else if (id_impianto != null && id_cartellone != null) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdCartellone(id_impianto, id_cartellone);
        } else if (id_impianto != null) {
            segnalazioni = reportRepository.findByIdImpianto(id_impianto);
        } else if (id_palinsesto != null && id_cartellone != null) {
            segnalazioni = reportRepository.findByIdPalinsestoAndIdCartellone(id_palinsesto, id_cartellone);
        } else if (id_palinsesto != null) {
            segnalazioni = reportRepository.findByIdPalinsesto(id_palinsesto);
        } else if (id_cartellone != null) {
            segnalazioni = reportRepository.findByIdCartellone(id_cartellone);
        } else {
            segnalazioni = reportRepository.findAll();
        }

        ArrayList<String> palinsestiPath = new ArrayList<>();

        for (Segnalazione s : segnalazioni) {
            String path = "Palinsesto: " + s.getIdPalinsesto() + " - Impianto: " + s.getIdImpianto();
            if (!palinsestiPath.contains(path)) {
                palinsestiPath.add(path);
            }
        }

        Collections.sort(palinsestiPath);

        model.addAttribute("palinsesti_path", palinsestiPath);

        // Stampo la lista
        System.out.println(palinsestiPath);

        // Ritorna la lista
        return palinsestiPath;
    }

    @GetMapping(path="/get_impressioni")
    public @ResponseBody HashMap<String, Integer> getImpressioni(@RequestParam(required = false) Integer id_impianto, @RequestParam(required = false) String id_palinsesto, @RequestParam(required = false) String id_cartellone, @RequestParam java.sql.Date from_date, @RequestParam Date to_date, Model model) {

        // Ottengo le segnalazioni in base alla modalità di filtraggio opportuna
        Iterable<Segnalazione> segnalazioni;

        if (id_impianto != null && id_palinsesto != null && id_cartellone != null) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdPalinsestoAndIdCartellone(id_impianto, id_palinsesto, id_cartellone);
        }
        else if (id_impianto != null && id_palinsesto != null) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdPalinsesto(id_impianto, id_palinsesto);
        }
        else if (id_impianto != null && id_cartellone != null) {
            segnalazioni = reportRepository.findByIdImpiantoAndIdCartellone(id_impianto, id_cartellone);
        }
        else if (id_impianto != null) {
            segnalazioni = reportRepository.findByIdImpianto(id_impianto);
        }
        else if (id_palinsesto != null && id_cartellone != null) {
            segnalazioni = reportRepository.findByIdPalinsestoAndIdCartellone(id_palinsesto, id_cartellone);
        }
        else if (id_palinsesto != null) {
            segnalazioni = reportRepository.findByIdPalinsesto(id_palinsesto);
        }
        else if (id_cartellone != null) {
            segnalazioni = reportRepository.findByIdCartellone(id_cartellone);
        }
        else {
            segnalazioni = reportRepository.findAll();
        }

        // Creo una HashMap contenente gli ID dei cartelloni e le impressioni di ciascuno di esso
        HashMap<String, Integer> impressioniCartelloni = new HashMap<>();

        // Itera tutte le segnalazioni
        for (Segnalazione s : segnalazioni) {

            System.out.println("Report timestamp: " + s.getTimestamp());
            System.out.println("From date: " + from_date);
            System.out.println("After date: " + to_date);

            if (s.getTimestamp().equals(from_date) || (s.getTimestamp().after(from_date)) && (s.getTimestamp().equals(to_date) || s.getTimestamp().before(to_date))) {
                // Se l'ID cartellone è già contenuto nell'HashMap, incrementa il numero di impressioni, altrimenti aggiungila all'HashMap
                if (impressioniCartelloni.containsKey(s.getIdCartellone())) {
                    impressioniCartelloni.replace(s.getIdCartellone(), impressioniCartelloni.get(s.getIdCartellone()) + 1);
                }
                else {
                    impressioniCartelloni.put(s.getIdCartellone(), 1);
                }
            }
        }

        model.addAttribute("impressioni_cartelloni", impressioniCartelloni);

        // Stampo la HashMap
        System.out.println(impressioniCartelloni);

        // Ritorna l'HashMap
        return impressioniCartelloni;

    }

    @GetMapping(path = "/get_report_data")
    public @ResponseBody Map<String, Object> getReportData(@RequestParam(required = false) Integer id_impianto, @RequestParam(required = false) String id_palinsesto, @RequestParam(required = false) String id_cartellone, @RequestParam Date from_date, @RequestParam Date to_date, Model model) {
        Map<String, Object> reportData = new HashMap<>();

        // Recupero cartelloni
        ArrayList<String> cartelloni = getCartelloni(model);

        // Recupero tempo impressioni
        HashMap<String, Double> tempoImpressioni = getTempoImpressioni(id_impianto, id_palinsesto, id_cartellone, from_date, to_date, model);

        // Recupero palinsesti path
        ArrayList<String> palinsestiPath = getPalinsestiPath(id_impianto, id_palinsesto, id_cartellone, model);

        // Recupero impressioni
        HashMap<String, Integer> impressioni = getImpressioni(id_impianto, id_palinsesto, id_cartellone, from_date, to_date, model);

        reportData.put("cartelloni", cartelloni);
        reportData.put("tempoImpressioni", tempoImpressioni);
        reportData.put("palinsestiPath", palinsestiPath);
        reportData.put("impressioni", impressioni);

        return reportData;
    }
}

package com.intellicrafters.ledwallmanager.services;
import com.intellicrafters.ledwallmanager.entities.Palinsesto;
import com.intellicrafters.ledwallmanager.repositories.PalinsestoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PalinsestoService {
    @Autowired
    PalinsestoRepository palinsestoRepository;

    public Palinsesto findPalinsestoById(String id_palinsesto) {
        return palinsestoRepository.findPalinsestoById(id_palinsesto);
    }

    public List<Palinsesto> findAll() {
        return palinsestoRepository.findAll();
    }
}

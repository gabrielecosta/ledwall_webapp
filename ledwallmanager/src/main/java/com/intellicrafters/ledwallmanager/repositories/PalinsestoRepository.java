package com.intellicrafters.ledwallmanager.repositories;

import com.intellicrafters.ledwallmanager.entities.Palinsesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PalinsestoRepository extends JpaRepository<Palinsesto, String> {
    @Query(value = "SELECT p FROM palinsesti p WHERE p.id_palinsesto = :id_palinsesto", nativeQuery = true)
    Palinsesto findPalinsestoById(@Param("id_palinsesto") String id_palinsesto);


}

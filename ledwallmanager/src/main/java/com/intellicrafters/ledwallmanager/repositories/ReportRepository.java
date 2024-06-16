package com.intellicrafters.ledwallmanager.repositories;

import com.intellicrafters.ledwallmanager.entities.Segnalazione;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<Segnalazione, Integer> {

    Iterable<Segnalazione> findByIdImpianto(int idImpianto);

    Iterable<Segnalazione> findByIdPalinsesto(String idPalinsesto);

    Iterable<Segnalazione> findByIdImpiantoAndIdPalinsesto(Integer idImpianto, String idPalinsesto);

    Iterable<Segnalazione> findByIdImpiantoAndIdCartellone(Integer idImpianto, String idCartellone);

    Iterable<Segnalazione> findByIdPalinsestoAndIdCartellone(String idPalinsesto, String idCartellone);

    Iterable<Segnalazione> findByIdImpiantoAndIdPalinsestoAndIdCartellone(Integer idImpianto, String idPalinsesto, String idCartellone);

    Iterable<Segnalazione> findByIdCartellone(String idCartellone);

}
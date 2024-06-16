package com.intellicrafters.ledwallmanager.repositories;

import com.intellicrafters.ledwallmanager.entities.Impianto;
import org.springframework.data.repository.CrudRepository;

public interface ImpiantoRepository extends CrudRepository<Impianto, Integer> {

    Impianto findByIdImpianto(int idImpianto);

}

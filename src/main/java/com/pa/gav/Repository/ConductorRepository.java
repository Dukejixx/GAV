package com.pa.gav.Repository;


import com.pa.gav.Entity.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConductorRepository extends JpaRepository<Conductor, Long> {

    List<Conductor> findByDisponible(boolean disponible);


    Conductor findByUsername(String username);

    Conductor findByCedula(String cedula);


    boolean existsByUsername(String username);;

    boolean existsByCedula(String cedula);

    boolean existsByEmail(String email);

    boolean existsByTelefono(String telefono);

    boolean existsByVehiculoId(Long vehiculoId);


    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByTelefonoAndIdNot(String telefono, Long id);

    boolean existsByCedulaAndIdNot(String cedula, Long id);

    boolean existsByUsernameAndIdNot(String username, Long id);

    boolean existsByVehiculoIdAndIdNot(Long vehiculoId, Long id);
}

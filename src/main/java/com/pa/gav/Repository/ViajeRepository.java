package com.pa.gav.Repository;

import com.pa.gav.Entity.Viaje;
import com.pa.gav.Entity.Cliente;
import com.pa.gav.Entity.Conductor;
import com.pa.gav.Entity.Viaje.EstadoViaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {



    List<Viaje> findByConductorAndEstadoViajeIn(Conductor conductor, List<Viaje.EstadoViaje> estados);

    List<Viaje> findByEstadoViaje(EstadoViaje estado);

    List<Viaje> findByCliente(Cliente cliente);

    List<Viaje> findByConductor(Conductor conductor);

    List<Viaje> findByFechaSolicitud(LocalDate fechaSolicitud);

    List<Viaje> findByClienteAndEstadoViaje(Cliente cliente, EstadoViaje estado);

    List<Viaje> findByConductorAndEstadoViaje(Conductor conductor, EstadoViaje estado);

    List<Viaje> findByFechaSolicitudBetween(LocalDate startDate, LocalDate endDate);

}


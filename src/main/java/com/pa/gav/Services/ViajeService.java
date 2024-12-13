package com.pa.gav.Services;



import com.pa.gav.Entity.Cliente;
import com.pa.gav.Entity.Conductor;
import com.pa.gav.Entity.Viaje;
import com.pa.gav.Repository.ClienteRepository;
import com.pa.gav.Repository.ConductorRepository;
import com.pa.gav.Repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConductorRepository conductorRepository;

    public String solicitarViaje(Long clienteId, String destino, int cantidadPasajeros, String fechaSolicitud) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (cantidadPasajeros <= 0) {
            throw new IllegalArgumentException("La cantidad de pasajeros debe ser mayor a 0.");
        }

        LocalDate fecha = LocalDate.parse(fechaSolicitud);
        if (fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de solicitud no puede ser anterior a la fecha actual.");
        }

        Viaje nuevoViaje = new Viaje();
        nuevoViaje.setDestino(destino);
        nuevoViaje.setCantidadPasajeros(cantidadPasajeros);
        nuevoViaje.setFechaSolicitud(fecha);
        nuevoViaje.setCliente(cliente);
        nuevoViaje.setEstadoViaje(Viaje.EstadoViaje.SOLICITADO);
        nuevoViaje.setPrecio(0.0);

        viajeRepository.save(nuevoViaje);

        return "Viaje solicitado con éxito.";
    }

    @Transactional
    public void cancelarViaje(Long viajeId) {

        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));

        if (viaje.getEstadoViaje() == Viaje.EstadoViaje.COMPLETADO || viaje.getEstadoViaje() == Viaje.EstadoViaje.EN_CURSO) {
            throw new IllegalStateException("El viaje no se puede cancelar porque ya está en curso o completado.");
        }

        if (viaje.getEstadoViaje() == Viaje.EstadoViaje.CANCELADO) {
            throw new IllegalStateException("El viaje ya está cancelado.");
        }

        viaje.setEstadoViaje(Viaje.EstadoViaje.CANCELADO);

        viajeRepository.save(viaje);
    }

    public List<Viaje> findByEstado(Viaje.EstadoViaje estado) {
        return viajeRepository.findByEstadoViaje(estado);
    }

    public Viaje findById(Long id) {
        return viajeRepository.findById(id).orElse(null);
    }

    public void update(Viaje viaje) {
        viajeRepository.save(viaje);
    }

    public String iniciarViaje(Long viajeId) {
        Optional<Viaje> optionalViaje = viajeRepository.findById(viajeId);

        if (optionalViaje.isPresent()) {
            Viaje viaje = optionalViaje.get();

            // Verificar que el estado del viaje sea ASIGNADO
            if (viaje.getEstadoViaje() == Viaje.EstadoViaje.ASIGNADO) {
                viaje.setEstadoViaje(Viaje.EstadoViaje.EN_CURSO);
                viajeRepository.save(viaje);
                return "El viaje ha comenzado exitosamente.";
            } else {
                return "No se puede iniciar el viaje. El estado actual es: " + viaje.getEstadoViaje();
            }
        } else {
            return "Viaje no encontrado.";
        }
    }

    public String finalizarViaje(Long viajeId) {
        Optional<Viaje> optionalViaje = viajeRepository.findById(viajeId);

        if (optionalViaje.isPresent()) {
            Viaje viaje = optionalViaje.get();

            if (viaje.getEstadoViaje() == Viaje.EstadoViaje.EN_CURSO) {
                viaje.setEstadoViaje(Viaje.EstadoViaje.COMPLETADO);


                Conductor conductor = viaje.getConductor();
                if (conductor != null) {
                    conductor.setDisponible(true);
                    conductorRepository.save(conductor);
                }

                viajeRepository.save(viaje);
                return "El viaje ha sido finalizado exitosamente.";
            } else {
                return "No se puede finalizar el viaje. El estado actual es: " + viaje.getEstadoViaje();
            }
        } else {
            return "Viaje no encontrado.";
        }
    }





}


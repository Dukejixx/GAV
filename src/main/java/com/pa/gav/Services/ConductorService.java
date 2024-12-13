package com.pa.gav.Services;

import com.pa.gav.Entity.Conductor;
import com.pa.gav.Repository.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ConductorService {

    @Autowired
    private ConductorRepository conductorRepository;

    public Conductor save(Conductor conductor) {
        // Validar campos únicos
        if (conductorRepository.existsByEmail(conductor.getEmail())) {
            throw new RuntimeException("El email " + conductor.getEmail() + " ya está registrado.");
        }
        if (conductorRepository.existsByTelefono(conductor.getTelefono())) {
            throw new RuntimeException("El teléfono " + conductor.getTelefono() + " ya está registrado.");
        }
        if (conductorRepository.existsByCedula(conductor.getCedula())) {
            throw new RuntimeException("La cédula " + conductor.getCedula() + " ya está registrada.");
        }
        if (conductorRepository.existsByUsername(conductor.getUsername())) {
            throw new RuntimeException("El username " + conductor.getUsername() + " ya está registrado.");
        }
        if (conductor.getVehiculo() != null && conductorRepository.existsByVehiculoId(conductor.getVehiculo().getId())) {
            throw new RuntimeException("El vehículo ya está asignado a otro conductor.");
        }
        return conductorRepository.save(conductor);
    }

    public void update(Conductor conductor) {
        // Validar campos únicos al actualizar
        if (conductorRepository.existsByEmailAndIdNot(conductor.getEmail(), conductor.getId())) {
            throw new RuntimeException("El email " + conductor.getEmail() + " ya está registrado.");
        }
        if (conductorRepository.existsByTelefonoAndIdNot(conductor.getTelefono(), conductor.getId())) {
            throw new RuntimeException("El teléfono " + conductor.getTelefono() + " ya está registrado.");
        }
        if (conductorRepository.existsByCedulaAndIdNot(conductor.getCedula(), conductor.getId())) {
            throw new RuntimeException("La cédula " + conductor.getCedula() + " ya está registrada.");
        }
        if (conductorRepository.existsByUsernameAndIdNot(conductor.getUsername(), conductor.getId())) {
            throw new RuntimeException("El username " + conductor.getUsername() + " ya está registrado.");
        }
        if (conductor.getVehiculo() != null && conductorRepository.existsByVehiculoIdAndIdNot(conductor.getVehiculo().getId(), conductor.getId())) {
            throw new RuntimeException("El vehículo ya está asignado a otro conductor.");
        }
        conductorRepository.save(conductor);
    }

    public Iterable<Conductor> findAll() {
        return conductorRepository.findAll();
    }

    public Conductor findById(Long id) {
        Optional<Conductor> optionalConductor = conductorRepository.findById(id);
        return optionalConductor.orElse(null);
    }

    public void delete(Long id) {
        conductorRepository.deleteById(id);
    }

    public void toggleAvailability(Long id) {
        Conductor conductor = findById(id);
        if (conductor != null) {
            conductor.setDisponible(!conductor.getDisponible());
            update(conductor);
        }
    }

    public List<Conductor> findByDisponible(boolean disponible) {
        return conductorRepository.findByDisponible(disponible);
    }
}

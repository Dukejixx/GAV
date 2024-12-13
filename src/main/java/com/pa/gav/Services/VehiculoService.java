package com.pa.gav.Services;

import com.pa.gav.Entity.Vehiculo;
import com.pa.gav.Exeptions.PlacaDuplicadaException;
import com.pa.gav.Repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public Vehiculo save(Vehiculo vehiculo) {
        if (vehiculoRepository.existsByPlaca(vehiculo.getPlaca())) {
            throw new PlacaDuplicadaException("La placa " + vehiculo.getPlaca() + " ya está registrada.");
        }
        return vehiculoRepository.save(vehiculo);
    }

    public void update(Vehiculo vehiculo) throws PlacaDuplicadaException {
        if (vehiculoRepository.existsByPlacaAndIdNot(vehiculo.getPlaca(), vehiculo.getId())) {
            throw new PlacaDuplicadaException("La placa " + vehiculo.getPlaca() + " ya está registrada.");
        }
        vehiculoRepository.save(vehiculo);
    }


    public Iterable<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo findById(Long id) {
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findById(id);
        return optionalVehiculo.orElse(null);
    }

    public void delete(Long id) {
        vehiculoRepository.deleteById(id);
    }

}

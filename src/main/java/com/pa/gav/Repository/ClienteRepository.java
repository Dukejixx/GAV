package com.pa.gav.Repository;


import com.pa.gav.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Long> {

    Cliente findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

    Cliente findByNumeroDocumento(String numeroDocumento);

    boolean existsByNumeroDocumento(String numeroDocumento);

    Cliente findByTelefono(String telefono);

    Cliente findByEmail(String email);




}

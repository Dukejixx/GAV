package com.pa.gav.Repository;

import com.pa.gav.Entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    Administrador findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);


}

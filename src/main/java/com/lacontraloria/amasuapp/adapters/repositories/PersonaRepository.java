package com.lacontraloria.amasuapp.adapters.repositories;

import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.domains.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Page<Persona> findAllByRoleType(RoleType roleType, PageRequest pageRequest);
    Page<Persona> findAllByRoleTypeAndDistritoActual(RoleType roleType, String distrito, PageRequest pageRequest);
    Optional<Persona> findByDniRieniecAndRoleType(Long dniRienic, RoleType roleType);
}

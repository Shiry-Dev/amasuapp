package com.lacontraloria.amasuapp.adapters.repositories;

import com.lacontraloria.amasuapp.domains.Administrador;
import com.lacontraloria.amasuapp.domains.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminstradorRepository extends JpaRepository<Administrador, Long> {
}

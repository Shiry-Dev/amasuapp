package com.lacontraloria.amasuapp.adapters.repositories;

import com.lacontraloria.amasuapp.domains.Alerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    Page<Alerta> findByCui(Long cui, PageRequest pageRequest);
}

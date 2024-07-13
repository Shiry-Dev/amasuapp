package com.lacontraloria.amasuapp.adapters.repositories;

import com.lacontraloria.amasuapp.domains.Observacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservacionRepository extends JpaRepository<Observacion, Long> {
}

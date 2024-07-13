package com.lacontraloria.amasuapp.adapters.repositories;

import com.lacontraloria.amasuapp.domains.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {
}

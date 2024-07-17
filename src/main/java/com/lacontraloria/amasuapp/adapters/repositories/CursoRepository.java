package com.lacontraloria.amasuapp.adapters.repositories;

import com.lacontraloria.amasuapp.domains.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}

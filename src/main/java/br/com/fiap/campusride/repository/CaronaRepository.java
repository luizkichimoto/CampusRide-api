package br.com.fiap.campusride.repository;

import br.com.fiap.campusride.model.Carona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaronaRepository extends JpaRepository<Carona, Long> {
}
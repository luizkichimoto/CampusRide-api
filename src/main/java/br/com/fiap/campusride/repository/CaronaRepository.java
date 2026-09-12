package br.com.fiap.campusride.repository;

import br.com.fiap.campusride.enums.SituacaoCarona;
import br.com.fiap.campusride.model.Carona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaronaRepository extends JpaRepository<Carona, Long> {
    List<Carona> findBySituacao(SituacaoCarona situacao);
}
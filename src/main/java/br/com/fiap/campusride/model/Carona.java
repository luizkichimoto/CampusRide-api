package br.com.fiap.campusride.model;

import br.com.fiap.campusride.enums.SituacaoCarona;
import br.com.fiap.campusride.enums.TipoVeiculo;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Carona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeMotorista;
    private String origem;
    private String destino;
    private LocalDateTime horarioPartida;

    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipoVeiculo;

    private Integer vagasTotais;
    private Integer vagasDisponiveis;

    @Enumerated(EnumType.STRING)
    private SituacaoCarona situacao;

    @OneToMany(mappedBy = "carona")
    private List<Reserva> reservas = new ArrayList<>();
}

package br.com.fiap.campusride.model;

import br.com.fiap.campusride.enums.SituacaoReserva;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carona_id")
    private Carona carona;

    private String nomePassageiro;
    private LocalDateTime dataHoraReserva;

    @Enumerated(EnumType.STRING)
    private SituacaoReserva situacao;
}
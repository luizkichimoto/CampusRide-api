package br.com.fiap.campusride.dto;

import br.com.fiap.campusride.enums.SituacaoReserva;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaResponseDTO {
    private Long id;
    private Long caronaId;
    private String nomePassageiro;
    private LocalDateTime dataHoraReserva;
    private SituacaoReserva situacao;
}
package br.com.fiap.campusride.dto;

import br.com.fiap.campusride.enums.TipoVeiculo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaronaRequestDTO {
        private String nomeMotorista;
        private String origem;
        private String destino;
        private LocalDateTime horarioPartida;
        private TipoVeiculo tipoVeiculo;
        private Integer vagasTotais;
}


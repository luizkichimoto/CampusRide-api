package br.com.fiap.campusride.dto;

import br.com.fiap.campusride.enums.TipoVeiculo;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaronaRequestDTO {

    @NotBlank(message = "O nome do motorista é obrigatório")
    private String nomeMotorista;

    @NotBlank(message = "A origem é obrigatória")
    private String origem;

    @NotBlank(message = "O destino é obrigatório")
    private String destino;

    @NotNull(message = "O horário de partida é obrigatório")
    @Future(message = "O horário de partida deve ser mais tarde")
    private LocalDateTime horarioPartida;

    @NotNull(message = "O tipo de veículo é obrigatório")
    private TipoVeiculo tipoVeiculo;

    @NotNull(message = "A quantidade de vagas é obrigatória")
    @Min(value = 1, message = "A carona deve ter pelo menos 1 vaga")
    private Integer vagasTotais;
}
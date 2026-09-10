package br.com.fiap.campusride.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservaRequestDTO {

    @NotNull(message = "O ID da carona é obrigatório")
    private Long caronaId;

    @NotBlank(message = "O nome do passageiro é obrigatório")
    private String nomePassageiro;
}
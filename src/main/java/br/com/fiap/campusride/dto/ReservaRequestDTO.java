package br.com.fiap.campusride.dto;

import lombok.Data;

@Data
public class ReservaRequestDTO {
    private Long caronaId;
    private String nomePassageiro;
}
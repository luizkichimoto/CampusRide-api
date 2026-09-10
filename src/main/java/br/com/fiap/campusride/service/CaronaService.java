package br.com.fiap.campusride.service;

import br.com.fiap.campusride.dto.CaronaRequestDTO;
import br.com.fiap.campusride.dto.CaronaResponseDTO;
import br.com.fiap.campusride.enums.SituacaoCarona;
import br.com.fiap.campusride.model.Carona;
import br.com.fiap.campusride.repository.CaronaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaronaService {

    private final CaronaRepository repository;

    public CaronaResponseDTO publicarCarona(CaronaRequestDTO dto) {
        Carona carona = new Carona();
        carona.setNomeMotorista(dto.getNomeMotorista());
        carona.setOrigem(dto.getOrigem());
        carona.setDestino(dto.getDestino());
        carona.setHorarioPartida(dto.getHorarioPartida());
        carona.setTipoVeiculo(dto.getTipoVeiculo());
        carona.setVagasTotais(dto.getVagasTotais());

        carona.setVagasDisponiveis(dto.getVagasTotais());
        carona.setSituacao(SituacaoCarona.ABERTA);

        Carona caronaSalva = repository.save(carona);

        return converterParaResponseDTO(caronaSalva);
    }

    public List<CaronaResponseDTO> listarCaronas() {
        List<Carona> caronas = repository.findAll();

        return caronas.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    private CaronaResponseDTO converterParaResponseDTO(Carona carona) {
        CaronaResponseDTO dto = new CaronaResponseDTO();
        dto.setId(carona.getId());
        dto.setNomeMotorista(carona.getNomeMotorista());
        dto.setOrigem(carona.getOrigem());
        dto.setDestino(carona.getDestino());
        dto.setHorarioPartida(carona.getHorarioPartida());
        dto.setTipoVeiculo(carona.getTipoVeiculo());
        dto.setVagasTotais(carona.getVagasTotais());
        dto.setVagasDisponiveis(carona.getVagasDisponiveis());
        dto.setSituacao(carona.getSituacao());
        return dto;
    }
}
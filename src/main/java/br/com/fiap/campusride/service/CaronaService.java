package br.com.fiap.campusride.service;

import br.com.fiap.campusride.dto.CaronaRequestDTO;
import br.com.fiap.campusride.dto.CaronaResponseDTO;
import br.com.fiap.campusride.enums.SituacaoCarona;
import br.com.fiap.campusride.enums.SituacaoReserva;
import br.com.fiap.campusride.exception.RegraNegocioException;
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

    public List<CaronaResponseDTO> listarTodasCaronas() {
        List<Carona> caronas = repository.findAll();

        return caronas.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public List<CaronaResponseDTO> listarCaronasDisponiveis() {
        // Busca caronas que estão com a situação ABERTA
        List<Carona> caronas = repository.findBySituacao(SituacaoCarona.ABERTA);

        // Converte cada Carona em CaronaResponseDTO
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

    public CaronaResponseDTO buscarPorId(Long id) {
        Carona carona = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Carona não encontrada!"));

        return converterParaResponseDTO(carona);
    }

    public CaronaResponseDTO cancelarCarona(Long id) {
        // Busca a carona pelo ID
        Carona carona = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Carona não encontrada!"));

        // Regra de Negócio: Impedir cancelamento de carona concluída
        if (carona.getSituacao() == SituacaoCarona.CONCLUIDA) {
            throw new RegraNegocioException("Não é possível cancelar uma carona já concluída.");
        }

        if (carona.getSituacao() == SituacaoCarona.CANCELADA) {
            throw new RegraNegocioException("Esta carona já foi cancelada.");
        }

        // Cancela a carona
        carona.setSituacao(SituacaoCarona.CANCELADA);

        // Reflete o cancelamento nas reservas associadas
        // (O if garante que não vai dar erro se a lista estiver vazia)
        if (carona.getReservas() != null) {
            carona.getReservas().forEach(reserva -> {
                if (reserva.getSituacao() != SituacaoReserva.CANCELADA) {
                    reserva.setSituacao(SituacaoReserva.CANCELADA);
                }
            });
        }

        //Salva no banco de dados (o Spring salva as reservas junto se o mapeamento estiver correto)
        Carona caronaCancelada = repository.save(carona);

        return converterParaResponseDTO(caronaCancelada);
    }


}
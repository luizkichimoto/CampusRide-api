package br.com.fiap.campusride.service;

import br.com.fiap.campusride.dto.ReservaRequestDTO;
import br.com.fiap.campusride.dto.ReservaResponseDTO;
import br.com.fiap.campusride.enums.SituacaoCarona;
import br.com.fiap.campusride.enums.SituacaoReserva;
import br.com.fiap.campusride.exception.RegraNegocioException;
import br.com.fiap.campusride.model.Carona;
import br.com.fiap.campusride.model.Reserva;
import br.com.fiap.campusride.repository.CaronaRepository;
import br.com.fiap.campusride.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final CaronaRepository caronaRepository;

    public ReservaResponseDTO reservarVaga(ReservaRequestDTO dto) {

        Carona carona = caronaRepository.findById(dto.getCaronaId())
                .orElseThrow(() -> new RuntimeException("Carona não encontrada!"));

        if (carona.getSituacao() != SituacaoCarona.ABERTA) {
            throw new RegraNegocioException("Só é possível reservar vagas em caronas com situação ABERTA.");
        }

        // 2. Impedir reserva em carona lotada (sem vagas)
        if (carona.getVagasDisponiveis() <= 0) {
            throw new RegraNegocioException("Não há vagas disponíveis nesta carona.");
        }

        Reserva reserva = new Reserva();
        reserva.setCarona(carona);
        reserva.setNomePassageiro(dto.getNomePassageiro());
        reserva.setDataHoraReserva(LocalDateTime.now());
        reserva.setSituacao(SituacaoReserva.CONFIRMADA);

        carona.setVagasDisponiveis(carona.getVagasDisponiveis() - 1);
        caronaRepository.save(carona);

        Reserva reservaSalva = reservaRepository.save(reserva);

        return converterParaResponseDTO(reservaSalva);
    }

    private ReservaResponseDTO converterParaResponseDTO(Reserva reserva) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId(reserva.getId());
        dto.setCaronaId(reserva.getCarona().getId());
        dto.setNomePassageiro(reserva.getNomePassageiro());
        dto.setDataHoraReserva(reserva.getDataHoraReserva());
        dto.setSituacao(reserva.getSituacao());
        return dto;
    }
}
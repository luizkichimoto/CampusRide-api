package br.com.fiap.campusride.service;

import br.com.fiap.campusride.dto.ReservaRequestDTO;
import br.com.fiap.campusride.dto.ReservaResponseDTO;
import br.com.fiap.campusride.enums.SituacaoReserva;
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
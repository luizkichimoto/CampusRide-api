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

        //Impedir reserva em carona que não seja ABERTA

        if (carona.getSituacao() != SituacaoCarona.ABERTA) {
            throw new RegraNegocioException("Só é possível reservar vagas em caronas com situação ABERTA.");
        }

        // Impedir reserva em carona lotada (sem vagas)
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

    public ReservaResponseDTO cancelarReserva(Long id) {
        // Busca a reserva pelo ID
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Reserva não encontrada!"));

        Carona carona = reserva.getCarona();

        // Regra de Negócio: Impedir cancelamento se a carona já foi concluída
        if (carona.getSituacao() == SituacaoCarona.CONCLUIDA) {
            throw new RegraNegocioException("Não é possível cancelar uma reserva de uma carona já concluída.");
        }

        //Regra extra de segurança: Evitar cancelar o que já está cancelado
        if (reserva.getSituacao() == SituacaoReserva.CANCELADA) {
            throw new RegraNegocioException("Esta reserva já foi cancelada anteriormente.");
        }

        // Atualiza a situação da reserva
        reserva.setSituacao(SituacaoReserva.CANCELADA);

        //Devolve a vaga para a carona
        carona.setVagasDisponiveis(carona.getVagasDisponiveis() + 1);

        // Se a carona estava lotada, agora ela volta a ficar aberta
        if (carona.getSituacao() == SituacaoCarona.LOTADA) {
            carona.setSituacao(SituacaoCarona.ABERTA);
        }

        // Salva tudo no banco
        caronaRepository.save(carona);
        Reserva reservaCancelada = reservaRepository.save(reserva);

        return converterParaResponseDTO(reservaCancelada);
    }
}
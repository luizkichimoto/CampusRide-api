package br.com.fiap.campusride.controller;

import br.com.fiap.campusride.dto.CaronaRequestDTO;
import br.com.fiap.campusride.dto.CaronaResponseDTO;
import br.com.fiap.campusride.service.CaronaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caronas")
@RequiredArgsConstructor
public class CaronaController {

    private final CaronaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CaronaResponseDTO publicar(@Valid @RequestBody CaronaRequestDTO request) {
        return service.publicarCarona(request);
    }

    //Rota para ver TODAS as caronas
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CaronaResponseDTO> listarTodas() {
        return service.listarTodasCaronas();
    }

    //Rota específica para ver apenas as caronas DISPONÍVEIS
    @GetMapping("/disponiveis")
    @ResponseStatus(HttpStatus.OK)
    public List<CaronaResponseDTO> listarDisponiveis() {
        return service.listarCaronasDisponiveis();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CaronaResponseDTO buscarCarona(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PatchMapping("/{id}/cancelar")
    @ResponseStatus(HttpStatus.OK)
    public CaronaResponseDTO cancelar(@PathVariable Long id) {
        return service.cancelarCarona(id);
    }

    @PatchMapping("/{id}/iniciar")
    @ResponseStatus(HttpStatus.OK)
    public CaronaResponseDTO iniciar(@PathVariable Long id) {
        return service.iniciarCarona(id);
    }

    @PatchMapping("/{id}/concluir")
    @ResponseStatus(HttpStatus.OK)
    public CaronaResponseDTO concluir(@PathVariable Long id) {
        return service.concluirCarona(id);
    }

}
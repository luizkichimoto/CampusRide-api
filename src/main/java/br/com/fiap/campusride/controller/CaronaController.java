package br.com.fiap.campusride.controller;

import br.com.fiap.campusride.dto.CaronaRequestDTO;
import br.com.fiap.campusride.dto.CaronaResponseDTO;
import br.com.fiap.campusride.service.CaronaService;
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
    public CaronaResponseDTO publicar(@RequestBody CaronaRequestDTO request) {
        return service.publicarCarona(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CaronaResponseDTO> listar() {
        return service.listarCaronas();
    }
}
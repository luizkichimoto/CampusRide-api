package br.com.fiap.campusride.controller;

import br.com.fiap.campusride.dto.ReservaRequestDTO;
import br.com.fiap.campusride.dto.ReservaResponseDTO;
import br.com.fiap.campusride.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponseDTO reservar(@Valid  @RequestBody ReservaRequestDTO request) {
        return service.reservarVaga(request);
    }
}
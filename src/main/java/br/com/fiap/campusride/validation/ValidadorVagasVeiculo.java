package br.com.fiap.campusride.validation;

import br.com.fiap.campusride.dto.CaronaRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorVagasVeiculo implements ConstraintValidator<ValidaVagasVeiculo, CaronaRequestDTO> {

    @Override
    public boolean isValid(CaronaRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getTipoVeiculo() == null || dto.getVagasTotais() == null) {
            return true;
        }

        boolean isValido = false;

        switch (dto.getTipoVeiculo()) {
            case MOTO:
                isValido = dto.getVagasTotais() <= 1;
                break;
            case CARRO:
                isValido = dto.getVagasTotais() <= 4;
                break;
            default:
                isValido = true;
                break;
        }

        if (!isValido) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("vagasTotais")
                    .addConstraintViolation();
        }

        return isValido;
    }
}
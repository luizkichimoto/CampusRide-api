package br.com.fiap.campusride.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidadorVagasVeiculo.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidaVagasVeiculo {

    String message() default "A quantidade de vagas não é permitida para este tipo de veículo";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
package com.oxeschool.api.dtos.curso;

import com.oxeschool.api.enums.StatusCurso;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EditarStatusCursoRequest {

    @NotBlank(message = "status é obrigatorio")
    private StatusCurso status;

}

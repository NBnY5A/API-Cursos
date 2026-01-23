package com.NBnY5A.cursos.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCourseRequestDTO(
        @NotBlank
        @Size(min = 2, message = "O campo [name] não pode ser deixado em branco e deve ter no mínimo 2 caracteres!")
        String name,

        @NotBlank(message = "O campo [category] não pode ser deixado em branco")
        String category,

        @NotNull(message = "O campo [isActive] é obrigatório e não pode ser deixado em branco")
        Boolean isActive,

        @NotNull(message = "O campo [id_professor] é obrigatório e não pode ser deixado em branco")
        Long id_professor
) {}


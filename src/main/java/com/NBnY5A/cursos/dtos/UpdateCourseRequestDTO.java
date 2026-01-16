package com.NBnY5A.cursos.dtos;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateCourseRequestDTO(
        @Size(min = 2, message = "O campo [name] deve ter mais do que dois caracteres!")
        String name,

        @Size(min = 2, message = "O campo [category] deve ter mais do que dois caracteres!")
        String category,

        @Positive(message = "O campo [teacherId] deve ser um Id válido e positivo!")
        Long teacherId
) {}

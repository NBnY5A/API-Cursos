package com.NBnY5A.cursos.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTeacherRequestDTO(
        @NotBlank(message = "O campo [fistName] não pode ser vazio!")
        @Size(min = 2, message = "O campo [firstName] deve ter no mínimo 2 caracteres!")
        String firstName,
        String lastName,

        @NotBlank(message = "O campo [email] não pode ser vazio!")
        @Email(message = "Forneça um [email] válido!")
        String email
) {}

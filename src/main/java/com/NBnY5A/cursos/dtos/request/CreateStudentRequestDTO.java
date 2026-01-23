package com.NBnY5A.cursos.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateStudentRequestDTO(
        @NotBlank(message = "O campo [firstName] não pode ser vazio ou deixado em branco!")
        @Size(min = 2, message = "O campo [firstName] deve conter mais do que dois caracteres!")
        String firstName,

        String lastName,

        @NotBlank(message = "O campo [email] não pode ser vazio ou deixado em branco")
        @Email(message = "O campo [email] precisa ser um email válido")
        String email,

        @NotBlank(message = "O campo [password] obrigatório e deve ter no mínimo 8 caracteres")
        @Size(min = 8)
        String password
) {}

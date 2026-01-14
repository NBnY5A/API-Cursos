package com.NBnY5A.cursos.dtos;

import com.NBnY5A.cursos.entities.Teacher;

public record CreateTeacherRequestDTO(
        String firstName,
        String lastName,
        String email
) {
    public Teacher toEntity() {
        return Teacher.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .build();
    }
}

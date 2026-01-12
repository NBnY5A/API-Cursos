package com.NBnY5A.cursos.dtos;

import com.NBnY5A.cursos.entities.Teacher;

public record CreateTeacherRequestDTO(
        String firstName,
        String lastName,
        String email
) {
    public Teacher toEntity() {
        return new Teacher(
                this.firstName,
                this.lastName,
                this.email
        );
    }
}

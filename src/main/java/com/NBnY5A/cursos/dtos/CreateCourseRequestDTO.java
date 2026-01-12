package com.NBnY5A.cursos.dtos;

import com.NBnY5A.cursos.entities.Course;
import com.NBnY5A.cursos.entities.Teacher;

public record CreateCourseRequestDTO(
        String name,
        String category,
        Boolean isActive,
        Long id_professor
) {
    public Course toEntity(Teacher teacher) {
        return new Course(
                this.name,
                this.category,
                this.isActive,
                teacher
        );
    }
}


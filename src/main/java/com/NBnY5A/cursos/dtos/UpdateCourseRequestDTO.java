package com.NBnY5A.cursos.dtos;

public record UpdateCourseRequestDTO(
        String name,
        String category,
        Long teacherId
) {
}

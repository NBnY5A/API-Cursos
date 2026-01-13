package com.NBnY5A.cursos.controllers;

public record UpdateCourseRequestDTO(
        String name,
        String category,
        Long teacherId
) {
}

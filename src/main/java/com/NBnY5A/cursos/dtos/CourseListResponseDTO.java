package com.NBnY5A.cursos.dtos;

public record CourseListResponseDTO(
        String name,
        String category,
        Boolean isActive,
        String teacherFirstName,
        String teacherEmail
) {}
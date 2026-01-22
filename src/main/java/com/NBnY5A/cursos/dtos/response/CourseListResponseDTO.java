package com.NBnY5A.cursos.dtos.response;

public record CourseListResponseDTO(
        String name,
        String category,
        Boolean isActive,
        String teacherFirstName,
        String teacherEmail
) {}
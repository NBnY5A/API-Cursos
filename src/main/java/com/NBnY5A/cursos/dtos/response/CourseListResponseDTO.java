package com.NBnY5A.cursos.dtos.response;

import lombok.Builder;

@Builder
public record CourseListResponseDTO(
        String name,
        String category,
        Boolean isActive,
        String teacherFirstName,
        String teacherEmail
) {}
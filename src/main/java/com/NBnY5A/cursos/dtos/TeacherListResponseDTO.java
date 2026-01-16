package com.NBnY5A.cursos.dtos;

import java.util.List;

public record TeacherListResponseDTO(
        String name,
        String email,
        List<TeacherCourseInfoDTO> courseList
) {}

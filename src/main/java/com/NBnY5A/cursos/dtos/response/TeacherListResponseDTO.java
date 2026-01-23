package com.NBnY5A.cursos.dtos.response;

import com.NBnY5A.cursos.dtos.TeacherCourseInfoDTO;

import java.util.List;

public record TeacherListResponseDTO(
        String name,
        String email,
        List<TeacherCourseInfoDTO> courseList
) {}

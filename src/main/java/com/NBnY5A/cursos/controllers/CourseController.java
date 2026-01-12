package com.NBnY5A.cursos.controllers;

import com.NBnY5A.cursos.dtos.CourseCreatedResponseDTO;
import com.NBnY5A.cursos.dtos.CreateCourseRequestDTO;
import com.NBnY5A.cursos.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<CourseCreatedResponseDTO> create(@RequestBody CreateCourseRequestDTO dto) {
        var course = this.courseService.create(dto);

        return ResponseEntity.ok().body(course);
    }
}

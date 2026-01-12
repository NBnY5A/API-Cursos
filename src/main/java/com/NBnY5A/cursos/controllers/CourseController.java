package com.NBnY5A.cursos.controllers;

import com.NBnY5A.cursos.dtos.CourseCreatedResponseDTO;
import com.NBnY5A.cursos.dtos.CourseListResponseDTO;
import com.NBnY5A.cursos.dtos.CreateCourseRequestDTO;
import com.NBnY5A.cursos.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jmx.ParentAwareNamingStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ParentAwareNamingStrategy parentAwareNamingStrategy;

    @PostMapping("/create")
    public ResponseEntity<CourseCreatedResponseDTO> create(@RequestBody CreateCourseRequestDTO dto) {
        var course = this.courseService.create(dto);

        return ResponseEntity.ok().body(course);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<CourseListResponseDTO>> listCourses() {
        var courses = this.courseService.fetchAllCourses();

        return ResponseEntity.ok().body(courses);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<CourseListResponseDTO>> listFilteredCourses(@RequestParam(required = false) MultiValueMap<String, String> params) {
        var courses = this.courseService.fetchCoursesWithFilter(params);
        return ResponseEntity.ok().body(courses);
    }
}

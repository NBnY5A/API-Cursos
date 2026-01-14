package com.NBnY5A.cursos.controllers;

import com.NBnY5A.cursos.dtos.CourseCreatedResponseDTO;
import com.NBnY5A.cursos.dtos.CourseListResponseDTO;
import com.NBnY5A.cursos.dtos.CreateCourseRequestDTO;
import com.NBnY5A.cursos.dtos.UpdateCourseRequestDTO;
import com.NBnY5A.cursos.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseCreatedResponseDTO> create(@RequestBody @Valid CreateCourseRequestDTO dto) {
        var course = this.courseService.create(dto);

        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CourseListResponseDTO>> listCourses() {
        var courses = this.courseService.fetchAllCourses();

        return ResponseEntity.ok().body(courses);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<CourseListResponseDTO>> listFilteredCourses(@RequestParam(required = false) MultiValueMap<String, String> params) {
        var courses = this.courseService.fetchCoursesWithFilter(params);
        return ResponseEntity.ok().body(courses);
    }

    @PutMapping(value = "/{course_id}")
    public ResponseEntity<Void> updateCourseById(@PathVariable(value = "course_id") String courseId, @RequestBody UpdateCourseRequestDTO updateCourseRequestDTO) {
        courseService.updateCourseById(courseId, updateCourseRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{course_id}")
    public ResponseEntity<String> delete(@PathVariable(value = "course_id") String id) {
        try {
            courseService.deleteCourseById(id);
            return ResponseEntity.ok().body("Curso Deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não foi possível deletar o curso informado!");
        }
    }

    @PatchMapping(value = "/{course_id}/active")
    public ResponseEntity<Void> updateCourseStatusById(@PathVariable(value = "course_id") String id) {
        courseService.updateCourseById(id);

        return ResponseEntity.ok().build();
    }
}

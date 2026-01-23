package com.NBnY5A.cursos.controllers;

import com.NBnY5A.cursos.dtos.request.CreateStudentRequestDTO;
import com.NBnY5A.cursos.dtos.response.StudentCreatedResponseDTO;
import com.NBnY5A.cursos.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentCreatedResponseDTO> create(@Valid @RequestBody CreateStudentRequestDTO studentRequestDTO) {
        var dto = studentService.createStudent(studentRequestDTO);

        return ResponseEntity.ok().body(dto);
    }
}

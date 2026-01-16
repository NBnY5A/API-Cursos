package com.NBnY5A.cursos.controllers;


import com.NBnY5A.cursos.dtos.CreateTeacherRequestDTO;
import com.NBnY5A.cursos.dtos.TeacherCreatedResponseDTO;
import com.NBnY5A.cursos.dtos.TeacherListResponseDTO;
import com.NBnY5A.cursos.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<TeacherCreatedResponseDTO> create(@RequestBody @Valid CreateTeacherRequestDTO dto) {
        var responseDTO = teacherService.create(dto);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<TeacherListResponseDTO>> fetchAllTeachers() {
        var dtos = teacherService.getAllTeachers();
        return ResponseEntity.ok().body(dtos);
    }
}

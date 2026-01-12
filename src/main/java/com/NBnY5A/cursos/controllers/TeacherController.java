package com.NBnY5A.cursos.controllers;


import com.NBnY5A.cursos.dtos.CreateTeacherRequestDTO;
import com.NBnY5A.cursos.dtos.TeacherCreatedResponseDTO;
import com.NBnY5A.cursos.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/create")
    public ResponseEntity<TeacherCreatedResponseDTO> create(@RequestBody CreateTeacherRequestDTO dto) {
        var responseDTO = teacherService.create(dto);
        return ResponseEntity.ok().body(responseDTO);
    }
}

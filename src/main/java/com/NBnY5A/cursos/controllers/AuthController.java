package com.NBnY5A.cursos.controllers;

import com.NBnY5A.cursos.dtos.request.LoginRequestDTO;
import com.NBnY5A.cursos.dtos.response.LoginResponseDTO;
import com.NBnY5A.cursos.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final StudentService studentService;

    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO responseDTO = studentService.verifyIfStudentExists(loginRequestDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

}

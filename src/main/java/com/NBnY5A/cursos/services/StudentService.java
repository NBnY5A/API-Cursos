package com.NBnY5A.cursos.services;

import com.NBnY5A.cursos.dtos.request.CreateStudentRequestDTO;
import com.NBnY5A.cursos.dtos.response.StudentCreatedResponseDTO;
import com.NBnY5A.cursos.entities.Student;
import com.NBnY5A.cursos.exceptions.UserAlreadyExistsException;
import com.NBnY5A.cursos.mappers.StudentMapper;
import com.NBnY5A.cursos.repositories.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, StudentMapper mapper, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.studentMapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }


    public StudentCreatedResponseDTO createStudent(CreateStudentRequestDTO dto) {
        Student student = studentMapper.dtoToEntity(dto);

        student.setIsActive(true);

        String encryptedPassword = passwordEncoder.encode(student.getPassword());

        student.setPassword(encryptedPassword);

        try {
            studentRepository.save(student);

            return new StudentCreatedResponseDTO("Usuário criado com sucesso! Você já pode usar a plataforma");

        } catch (Exception e) {
            throw new UserAlreadyExistsException("Um usuário com esse e-mail já existe na base de dados!");
        }
    }
}

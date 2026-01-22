package com.NBnY5A.cursos.services;

import com.NBnY5A.cursos.dtos.request.CreateStudentRequestDTO;
import com.NBnY5A.cursos.dtos.response.StudentCreatedResponseDTO;
import com.NBnY5A.cursos.entities.Student;
import com.NBnY5A.cursos.exceptions.UserAlreadyExistsException;
import com.NBnY5A.cursos.mappers.StudentMapper;
import com.NBnY5A.cursos.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper mapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = mapper;
    }


    public StudentCreatedResponseDTO createStudent(CreateStudentRequestDTO dto) {
        Student student = studentMapper.dtoToEntity(dto);

        student.setIsActive(true);

        try {
            studentRepository.save(student);

            return new StudentCreatedResponseDTO("Usuário criado com sucesso! Você já pode usar a plataforma");

        } catch (Exception e) {
            throw new UserAlreadyExistsException("Um usuário com esse e-mail já existe na base de dados!");
        }
    }
}

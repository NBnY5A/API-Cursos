package com.NBnY5A.cursos.services;

import com.NBnY5A.cursos.dtos.CreateTeacherRequestDTO;
import com.NBnY5A.cursos.dtos.TeacherCreatedResponseDTO;
import com.NBnY5A.cursos.entities.Teacher;
import com.NBnY5A.cursos.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;


    public TeacherCreatedResponseDTO create(CreateTeacherRequestDTO dto) {
        Teacher teacher = dto.toEntity();

        var teacherCreated = this.teacherRepository.save(teacher);

        return new TeacherCreatedResponseDTO("Professor criado com sucesso!", teacherCreated.getId());
    }
}

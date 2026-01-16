package com.NBnY5A.cursos.services;

import com.NBnY5A.cursos.dtos.CreateTeacherRequestDTO;
import com.NBnY5A.cursos.dtos.TeacherCreatedResponseDTO;
import com.NBnY5A.cursos.dtos.TeacherListResponseDTO;
import com.NBnY5A.cursos.entities.Teacher;
import com.NBnY5A.cursos.mappers.TeacherMapper;
import com.NBnY5A.cursos.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper mapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = mapper;
    }

    public TeacherCreatedResponseDTO create(CreateTeacherRequestDTO dto) {
        Teacher teacher = teacherMapper.convertDtoToEntity(dto);

        var teacherCreated = this.teacherRepository.save(teacher);

        return new TeacherCreatedResponseDTO("Professor criado com sucesso!", teacherCreated.getId());
    }

    public List<TeacherListResponseDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();

        return teachers.stream().map(
                teacherMapper::toListDto
        ).toList();
    }
}

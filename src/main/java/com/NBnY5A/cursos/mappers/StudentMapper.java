package com.NBnY5A.cursos.mappers;

import com.NBnY5A.cursos.dtos.request.CreateStudentRequestDTO;
import com.NBnY5A.cursos.entities.Student;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StudentMapper {

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    Student dtoToEntity(CreateStudentRequestDTO studentRequestDTO);
}

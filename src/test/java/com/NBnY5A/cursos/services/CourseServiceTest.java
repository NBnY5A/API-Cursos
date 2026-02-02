package com.NBnY5A.cursos.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.NBnY5A.cursos.dtos.request.CreateCourseRequestDTO;
import com.NBnY5A.cursos.dtos.request.UpdateCourseRequestDTO;
import com.NBnY5A.cursos.dtos.response.CourseCreatedResponseDTO;
import com.NBnY5A.cursos.dtos.response.CourseListResponseDTO;
import com.NBnY5A.cursos.entities.Course;
import com.NBnY5A.cursos.entities.Teacher;
import com.NBnY5A.cursos.exceptions.CourseNotFoundException;
import com.NBnY5A.cursos.exceptions.TeacherNotFoundException;
import com.NBnY5A.cursos.mappers.CourseMapper;
import com.NBnY5A.cursos.repositories.CourseRepository;
import com.NBnY5A.cursos.repositories.TeacherRepository;

@ExtendWith(MockitoExtension.class)

class CourseServiceTest {
    
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;


    @Test
    @DisplayName("Should Create A Course Correctly")
    void shouldCreateCourseCorrectly() {
        CreateCourseRequestDTO dto = new CreateCourseRequestDTO("Test", "Test Course", true, 1L);
        
        Teacher teacher = Teacher.builder().id(1L).build();

        Course course = Course.builder().id(UUID.randomUUID().toString()).name("Test").category("Test Course").active(true).teacher(teacher).build();

        when(teacherRepository.findById(dto.id_professor())).thenReturn(Optional.of(teacher));

        when(courseMapper.convertDtoToEntity(dto)).thenReturn(course);

        when(courseRepository.save(any(Course.class))).thenReturn(course);
        
        CourseCreatedResponseDTO responseDTO = new CourseCreatedResponseDTO("Curso criado com sucesso!", course.getId());

        var actualResponse = courseService.create(dto);

        verify(teacherRepository, times(1)).findById(dto.id_professor());

        verify(courseMapper, times(1)).convertDtoToEntity(dto);

        verify(courseRepository, times(1)).save(any(Course.class));

        assertEquals(responseDTO, actualResponse);

    }

    @Test
    @DisplayName("Should Throws TeacherNotFoundException When Teacher Id On DTO Is Incorrect")
    void shouldThowsExceptionWhenTeacherIdIsIncorrect() {
        CreateCourseRequestDTO dto = new CreateCourseRequestDTO("Teste",
        "Teste Course", false, 10L);

        when(teacherRepository.findById(dto.id_professor())).thenReturn(Optional.empty());

        Exception exception = assertThrows(TeacherNotFoundException.class, () -> courseService.create(dto));

        assertEquals("Erro ao tentar encontrar professor!", exception.getMessage());
    }

    @Test
    @DisplayName("Should Return All Courses Correctly")
    void shouldReturnAllCoursesCorrectly() {
        List<CourseListResponseDTO> expectedResult = new ArrayList<>();

        List<Course> mockedList = List.of(Course.builder()
        .name("Teste")
        .category("Teste Category")
        .active(true)
        .teacher(Teacher.builder()
        .firstName("Teste Teacher First Name")
        .email("test.email@gmail.com").build())
        .build());

        CourseListResponseDTO course = new CourseListResponseDTO(
            "Teste",
            "Teste Category",
            true,
            "Teste Teacher First Name",
            "test.email@gmail.com"
        );

        expectedResult.add(course);

        when(courseRepository.findAll()).thenReturn(mockedList);

        var atualResult = courseService.fetchAllCourses();

        assertEquals(expectedResult, atualResult);
        assertEquals(1, atualResult.size());
        assertEquals("Teste", atualResult.get(0).name());
        assertEquals("Teste Category", atualResult.get(0).category());
        assertEquals(true, atualResult.get(0).isActive());
        assertEquals("Teste Teacher First Name", atualResult.get(0).teacherFirstName());
        assertEquals("test.email@gmail.com", atualResult.get(0).teacherEmail());
    }

    @Test
    @DisplayName("Should Filter Courses By Name Correctly")
    void shouldFilterCoursesByNameCorrectly() {
        String filterValue = "Java Course";
        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add("name", filterValue);

        Teacher teacher = Teacher.builder()
                .firstName("Teacher Name")
                .email("teacher@mail.com")
                .build();

        Course course = Course.builder()
                .name(filterValue)
                .category("IT")
                .active(true)
                .teacher(teacher)
                .build();

        when(courseRepository.findCourseByName(filterValue)).thenReturn(Optional.of(List.of(course)));

        var result = courseService.fetchCoursesWithFilter(filterParams);

        assertAll("Verify filter response",
            () -> assertEquals(1, result.size()),
            () -> assertEquals(course.getName(), result.get(0).name()),
            () -> assertEquals(course.getCategory(), result.get(0).category()),
            () -> assertEquals(course.getActive(), result.get(0).isActive()),
            () -> assertEquals(course.getTeacher().getFirstName(), result.get(0).teacherFirstName()),
            () -> assertEquals(course.getTeacher().getEmail(), result.get(0).teacherEmail())
        );

        verify(courseRepository, times(1)).findCourseByName(filterValue);
        verify(courseRepository, never()).findCourseByCategory(anyString());
    }

    @Test
    @DisplayName("Should Filter Courses By Category Correctly")
    void shouldFilterCoursesByCategoryCorrectly() {
        Teacher teacher = Teacher.builder()
                .firstName("Teste Teacher")
                .email("test.email@gmail.com")
                .build();

        Course course = Course.builder()
                .name("Spring Web")
                .category("Spring Boot")
                .active(true)
                .teacher(teacher)
                .build();

        String filter = "Spring Boot";

        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();

        filterParams.add("category", filter);

        when(courseRepository.findCourseByCategory(filter)).thenReturn(Optional.of(List.of(course)));

        var result = courseService.fetchCoursesWithFilter(filterParams);

        assertAll("Verify filter response", 
            () -> assertEquals(1, result.size()),
            () -> assertEquals(course.getName(), result.get(0).name()),
            () -> assertEquals(course.getCategory(), result.get(0).category()),
            () -> assertEquals(course.getActive(), result.get(0).isActive()),
            () -> assertEquals(course.getTeacher().getFirstName(), result.get(0).teacherFirstName()),
            () -> assertEquals(course.getTeacher().getEmail(), result.get(0).teacherEmail())
        );
        verify(courseRepository, times(1)).findCourseByCategory(filter);
        verify(courseRepository, never()).findCourseByName(anyString());

    }

    @Test
    @DisplayName("Should Throw CourseNotFoundException When Filter By Name Doesnt Exists")
    void shouldThrowExceptionWhenFilterByNameDoesntExists() {
        String filterName = "Curso Teste";
        
        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();

        filterParams.add("name", filterName);

        Exception exception = assertThrows(CourseNotFoundException.class,
             () -> courseService.fetchCoursesWithFilter(filterParams));

        assertEquals("Erro ao tentar buscar cursos!", exception.getMessage());
        verify(courseRepository, never()).findCourseByCategory(filterName);
    }

    @Test
    @DisplayName("Should Throw CourseNotFoundException When Filter By Category Doesnt Exists")
    void shouldThrowExceptionWhenFilterByCategoryDoesntExists() {
        String filterCategory = "Spring Boot";
        
        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();

        filterParams.add("category", filterCategory);

        Exception exception = assertThrows(CourseNotFoundException.class,
             () -> courseService.fetchCoursesWithFilter(filterParams));

        assertEquals("Erro ao tentar buscar cursos!", exception.getMessage());
        verify(courseRepository, never()).findCourseByName(filterCategory);
    }

    @Test
    @DisplayName("Should Update Course Information By Id Correctly")
    void shouldUpdateCourseInfoCorrectly() {

        long teacherId = 31L;

        Teacher teacher = Teacher.builder()
        .id(teacherId)
        .build();

        UpdateCourseRequestDTO dto = new UpdateCourseRequestDTO(
            "New Test Name", "New Category", teacher.getId());

        String courseId = UUID.randomUUID().toString();

        Course course = Course.builder()
        .id(courseId)
        .name("Test Name")
        .category("Test Course")
        .teacher(null)
        .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        courseService.updateCourseById(courseId, dto);

        assertAll("Verify Update Status",
            () -> assertEquals(dto.name(), course.getName()),
            () -> assertEquals(dto.category(), course.getCategory()),
            () -> assertEquals(dto.teacherId(), course.getTeacher().getId())
        );

        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("Should Update Course Status By Id Correctly")
    void shouldUpdateCourseStatusCorrectly() {

        boolean expectedStatus = true;

        String id = UUID.randomUUID().toString();

        Course course = Course.builder()
        .id(id)
        .active(false).build();

        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        courseService.updateCourseById(id);

        assertEquals(expectedStatus, course.getActive());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("Should Throw CourseNotFoundException When Id Is Wrong")
    void shouldNotUpdateCourseStatus() {

        String id = UUID.randomUUID().toString();

        Exception exception = assertThrows(CourseNotFoundException.class, 
            () -> courseService.updateCourseById(id));

        assertEquals("Não foi possível achar o curso pelo id informado!", exception.getMessage());
        verify(courseRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Should Delete Course By Id Correctly")
    void shouldDeleteCourseByIdCorrectly() {
        String courseId = UUID.randomUUID().toString();

        Course course = Course.builder().id(courseId).build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        courseService.deleteCourseById(courseId);

        verify(courseRepository, times(1)).deleteById(course.getId());    }

    @Test
    @DisplayName("Should Not Delete Course By Id When Id Is Wrong")
    void shouldNotDeleteCourseWithWrongId() {
        String id = UUID.randomUUID().toString();

        Exception exception = assertThrows(CourseNotFoundException.class,
            () -> courseService.deleteCourseById(id));

        assertEquals("Não foi possível achar o curso pelo id informado!", exception.getMessage());
    }
}
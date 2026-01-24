package com.NBnY5A.cursos.services;

import com.NBnY5A.cursos.dtos.request.CreateStudentRequestDTO;
import com.NBnY5A.cursos.dtos.request.LoginRequestDTO;
import com.NBnY5A.cursos.dtos.response.LoginResponseDTO;
import com.NBnY5A.cursos.dtos.response.StudentCreatedResponseDTO;
import com.NBnY5A.cursos.entities.Student;
import com.NBnY5A.cursos.exceptions.UserAlreadyExistsException;
import com.NBnY5A.cursos.mappers.StudentMapper;
import com.NBnY5A.cursos.repositories.StudentRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    public StudentService(StudentRepository studentRepository, StudentMapper mapper, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.studentRepository = studentRepository;
        this.studentMapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
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

    public LoginResponseDTO verifyIfStudentExists(LoginRequestDTO dto) {

            Optional<Student> student = studentRepository.findStudentByEmail(dto.email());

            if (student.isEmpty()) {
                throw new UsernameNotFoundException("Usuário não encontrado na base de dados!");
            }

            String studentPassword = student.get().getPassword();

            boolean passwordMatches = passwordEncoder.matches(dto.password(), studentPassword);

            if (!passwordMatches) {
                throw new IllegalArgumentException("Senha incorreta");
            }

            Instant now = Instant.now();
            long expiresIn = 420L;

            var claims = JwtClaimsSet.builder()
                    .issuer("victor")
                    .subject(student.get().getEmail())
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiresIn))
                    .build();

            var token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return new LoginResponseDTO(token, expiresIn);
    }
}

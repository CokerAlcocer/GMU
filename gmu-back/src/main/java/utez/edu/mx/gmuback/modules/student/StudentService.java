package utez.edu.mx.gmuback.modules.student;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.gmuback.utils.CustomResponse;
import utez.edu.mx.gmuback.utils.EnrollmentGenerator;
import utez.edu.mx.gmuback.kernel.enums.HttpResponse;
import utez.edu.mx.gmuback.kernel.enums.Role;
import utez.edu.mx.gmuback.kernel.dto.PersonDTO;
import utez.edu.mx.gmuback.modules.student.dto.StudentDTO;
import utez.edu.mx.gmuback.modules.user.User;
import utez.edu.mx.gmuback.modules.user.UserRepository;
import utez.edu.mx.gmuback.security.PassTools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<CustomResponse> findAllStudents() {
        List<Student> list = studentRepository.findAll();
        CustomResponse response = new CustomResponse(
                HttpResponse.OK.getMessage(),
                convertEntities(list),
                HttpResponse.OK.getStatus()
        );

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CustomResponse> findStudentById(Long id) {
        Student found = studentRepository.findById(id).orElse(null);
        boolean flag = found != null;
        CustomResponse response = new CustomResponse(
                flag ? HttpResponse.OK.getMessage() : HttpResponse.NOT_FOUND.getMessage(),
                flag ? convertEntity(found) : null,
                !flag, // Si se encontró error = false, Si NO se encontró error = true
                flag ? HttpResponse.OK.getStatus() : HttpResponse.NOT_FOUND.getStatus()
        );

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<CustomResponse> saveStudent(PersonDTO dto) {
        CustomResponse response = null;
        try {
            // Crear el usuario
            User u = new User();
            u.setUsername(dto.getUsername());
            u.setPassword(PassTools.generateSecureHashedPassword(dto.getUsername(), dto.getFullname()));
            u.setRole(Role.STUDENT.getId());
            u = userRepository.saveAndFlush(u);

            // Crear el alumno
            Student s = new Student();
            s.setFullname(dto.getFullname());
            s.setEnroll(EnrollmentGenerator.generateEnroll());
            s.setEmail(dto.getEmail());
            s.setUser(u);
            studentRepository.saveAndFlush(s);

            response = new CustomResponse(
                    HttpResponse.CREATED.getMessage(),
                    HttpResponse.CREATED.getStatus()
            );
        } catch (Exception ex) {
            response = new CustomResponse(
                    HttpResponse.INTERNAL_SERVER_ERROR.getMessage(),
                    true,
                    HttpResponse.INTERNAL_SERVER_ERROR.getStatus()
            );
        }

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<CustomResponse> updateOrDeleteStudent(PersonDTO dto, boolean isDelete) {
        CustomResponse response = null;
        Student found = studentRepository.findById(dto.getId()).orElse(null);

        if(found != null) {
            try {
                if(isDelete) {
                    studentRepository.deleteById(found.getId());
                    userRepository.deleteById(found.getUser().getId());
                } else {
                    found.setFullname(dto.getFullname());
                    found.setEmail(dto.getEmail());

                    studentRepository.saveAndFlush(found);
                }

                response = new CustomResponse(
                        isDelete ? HttpResponse.DELETED.getMessage() : HttpResponse.UPDATED.getMessage(),
                        isDelete ? HttpResponse.DELETED.getStatus() : HttpResponse.UPDATED.getStatus()
                );
            } catch (Exception ex) {
                response = new CustomResponse(
                        HttpResponse.INTERNAL_SERVER_ERROR.getMessage(),
                        true,
                        HttpResponse.INTERNAL_SERVER_ERROR.getStatus()
                );
            }
        } else {
            response = new CustomResponse(
                    HttpResponse.NOT_FOUND.getMessage(),
                    true,
                    HttpResponse.NOT_FOUND.getStatus()
            );
        }

        return new ResponseEntity<>(response, response.getStatus());
    }

    // Funciones de utilería
    private StudentDTO convertEntity(Student s) {
        return new StudentDTO(
                s.getId(),
                s.getEnroll(),
                s.getFullname(),
                s.getUser().getUsername(),
                s.getEmail()
        );
    }

    private List<StudentDTO> convertEntities(List<Student> sl) {
        List<StudentDTO> l = new ArrayList<>();
        for(Student s: sl) {
            l.add(new StudentDTO(
                    s.getId(),
                    s.getEnroll(),
                    s.getFullname(),
                    s.getUser().getUsername(),
                    s.getEmail()
            ));
        }

        return l;
    }
}

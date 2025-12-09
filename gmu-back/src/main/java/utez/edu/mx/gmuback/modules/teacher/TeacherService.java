package utez.edu.mx.gmuback.modules.teacher;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.gmuback.kernel.dto.PersonDTO;
import utez.edu.mx.gmuback.kernel.enums.HttpResponse;
import utez.edu.mx.gmuback.kernel.enums.Role;
import utez.edu.mx.gmuback.modules.teacher.dto.TeacherDTO;
import utez.edu.mx.gmuback.modules.user.User;
import utez.edu.mx.gmuback.modules.user.UserRepository;
import utez.edu.mx.gmuback.security.PassTools;
import utez.edu.mx.gmuback.utils.CustomResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<CustomResponse> findAllTeachers() {
        List<Teacher> list = teacherRepository.findAll();
        CustomResponse response = new CustomResponse(
                HttpResponse.OK.getMessage(),
                convertEntities(list),
                HttpResponse.OK.getStatus()
        );

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CustomResponse> findTeacherById(Long id) {
        Teacher found = teacherRepository.findById(id).orElse(null);
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
    public ResponseEntity<CustomResponse> saveTeacher(PersonDTO dto) {
        CustomResponse response = null;
        try {
            // Crear el usuario
            User u = new User();
            u.setUsername(dto.getUsername());
            u.setPassword(PassTools.generateSecureHashedPassword(dto.getUsername(), dto.getFullname()));
            u.setRole(Role.TEACHER.getId());
            u = userRepository.saveAndFlush(u);

            // Crear al maestro
            Teacher t = new Teacher();
            t.setFullname(dto.getFullname());
            t.setEmail(dto.getEmail());
            t.setUser(u);
            teacherRepository.saveAndFlush(t);

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
    public ResponseEntity<CustomResponse> updateOrDeleteTeacher(PersonDTO dto, boolean isDelete) {
        CustomResponse response = null;
        Teacher found = teacherRepository.findById(dto.getId()).orElse(null);

        if(found != null) {
            try {
                if(isDelete) {
                    teacherRepository.deleteById(found.getId());
                    userRepository.deleteById(found.getUser().getId());
                } else {
                    found.setFullname(dto.getFullname());
                    found.setEmail(dto.getEmail());

                    teacherRepository.saveAndFlush(found);
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
    private TeacherDTO convertEntity(Teacher s) {
        return new TeacherDTO(
                s.getId(),
                s.getFullname(),
                s.getUser().getUsername(),
                s.getEmail()
        );
    }

    private List<TeacherDTO> convertEntities(List<Teacher> tl) {
        List<TeacherDTO> l = new ArrayList<>();
        for(Teacher t: tl) {
            l.add(new TeacherDTO(
                    t.getId(),
                    t.getFullname(),
                    t.getUser().getUsername(),
                    t.getEmail()
            ));
        }

        return l;
    }
}

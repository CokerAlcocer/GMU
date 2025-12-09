package utez.edu.mx.gmuback.modules.teacher;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.gmuback.kernel.dto.PersonDTO;
import utez.edu.mx.gmuback.utils.CustomResponse;

@RestController
@RequestMapping("/gmu-api/teacher")
@AllArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    
    @GetMapping("")
    public ResponseEntity<CustomResponse> findAll() {
        return teacherService.findAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> findById(@PathVariable("id") Long id) {
        return teacherService.findTeacherById(id);
    }

    @PostMapping("")
    public ResponseEntity<CustomResponse> save(@RequestBody PersonDTO dto) {
        return teacherService.saveTeacher(dto);
    }

    @PutMapping("")
    public ResponseEntity<CustomResponse> update(@RequestBody PersonDTO dto) {
        return teacherService.updateOrDeleteTeacher(dto, false);
    }

    @DeleteMapping("")
    public ResponseEntity<CustomResponse> remove(@RequestBody PersonDTO dto) {
        return teacherService.updateOrDeleteTeacher(dto, true);
    }
}

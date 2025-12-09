package utez.edu.mx.gmuback.modules.student;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.gmuback.utils.CustomResponse;
import utez.edu.mx.gmuback.kernel.dto.PersonDTO;

@RestController
@RequestMapping("/gmu-api/student")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("")
    public ResponseEntity<CustomResponse> findAll() {
        return studentService.findAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> findById(@PathVariable("id") Long id) {
        return studentService.findStudentById(id);
    }

    @PostMapping("")
    public ResponseEntity<CustomResponse> save(@RequestBody PersonDTO dto) {
        return studentService.saveStudent(dto);
    }

    @PutMapping("")
    public ResponseEntity<CustomResponse> update(@RequestBody PersonDTO dto) {
        return studentService.updateOrDeleteStudent(dto, false);
    }

    @DeleteMapping("")
    public ResponseEntity<CustomResponse> remove(@RequestBody PersonDTO dto) {
        return studentService.updateOrDeleteStudent(dto, true);
    }
}

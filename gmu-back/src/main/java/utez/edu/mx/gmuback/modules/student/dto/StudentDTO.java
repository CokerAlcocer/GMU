package utez.edu.mx.gmuback.modules.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {
    private Long id;
    private String enroll;
    private String fullname;
    private String username;
    private String email;
}

package utez.edu.mx.gmuback.modules.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeacherDTO {
    private Long id;
    private String fullname;
    private String username;
    private String email;
}

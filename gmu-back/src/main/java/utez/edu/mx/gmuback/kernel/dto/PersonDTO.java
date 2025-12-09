package utez.edu.mx.gmuback.kernel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonDTO {
    private Long id;
    private String username;
    private String fullname;
    private String email;
}

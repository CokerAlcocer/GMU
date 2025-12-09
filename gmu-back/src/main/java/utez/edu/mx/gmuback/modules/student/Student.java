package utez.edu.mx.gmuback.modules.student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.gmuback.kernel.templates.PersonTemplate;

@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student extends PersonTemplate {
    @Column(name = "enroll", unique = true, nullable = false)
    private String enroll;
}

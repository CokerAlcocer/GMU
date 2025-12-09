package utez.edu.mx.gmuback.modules.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.gmuback.kernel.templates.EntityTemplate;
import utez.edu.mx.gmuback.modules.student.Student;
import utez.edu.mx.gmuback.modules.teacher.Teacher;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends EntityTemplate {
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private Integer role;

    @OneToOne(mappedBy = "user")
    private Student person;

    @OneToOne(mappedBy = "user")
    private Teacher teacher;
}

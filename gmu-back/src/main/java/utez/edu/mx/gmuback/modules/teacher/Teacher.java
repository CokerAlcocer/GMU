package utez.edu.mx.gmuback.modules.teacher;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import utez.edu.mx.gmuback.kernel.templates.PersonTemplate;

@Entity
@Table(name = "teacher")
@Getter
@Setter
public class Teacher extends PersonTemplate {

}

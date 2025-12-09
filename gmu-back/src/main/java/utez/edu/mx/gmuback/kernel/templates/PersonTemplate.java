package utez.edu.mx.gmuback.kernel.templates;

import jakarta.persistence.*;
import lombok.*;
import utez.edu.mx.gmuback.modules.user.User;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonTemplate extends EntityTemplate {
    @Column(name = "fullname", nullable = false, unique = true)
    private String fullname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false, unique = true)
    private User user;
}

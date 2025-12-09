package utez.edu.mx.gmuback.kernel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    PRINCIPAL(1, "ROLE_PRINCIPAL"),
    TEACHER(2, "ROLE_TEACHER"),
    STUDENT(3, "ROLE_STUDENT");

    private final int id;
    private final String authority;
}

package utez.edu.mx.gmuback.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDate;

public class PassTools {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private static String encodePassword(String r) {
        return encoder.encode(r);
    }

    public static boolean matchPassword(String r, String e) {
        return encoder.matches(r, e);
    }

    public static String generateSecureHashedPassword(String u, String f) {
        String r = "#" + LocalDate.now().getYear() + u + f.toLowerCase().charAt(0);
        return encodePassword(r);
    }
}

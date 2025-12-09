package utez.edu.mx.gmuback.utils;

import java.time.LocalDateTime;

public class EnrollmentGenerator {
    public static String generateEnroll() {
        LocalDateTime now = LocalDateTime.now();
        return now.getYear() +
                "GMU" +
                String.format("%02d", now.getMonthValue()) +
                String.format("%02d", now.getDayOfMonth()) +
                "-" +
                String.format("%02d", now.getHour()) +
                String.format("%02d", now.getMinute()) +
                String.format("%02d", now.getSecond());
    }
}

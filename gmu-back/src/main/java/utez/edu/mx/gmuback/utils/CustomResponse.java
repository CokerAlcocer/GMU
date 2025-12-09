package utez.edu.mx.gmuback.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomResponse {
    private String message;
    private Object data;
    private boolean error;
    private HttpStatus status;

    public CustomResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public CustomResponse(String message, Object data, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public CustomResponse(String message, boolean error, HttpStatus status) {
        this.message = message;
        this.error = error;
        this.status = status;
    }

    public CustomResponse(String message, Object data, boolean error, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.error = error;
        this.status = status;
    }
}

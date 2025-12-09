package utez.edu.mx.gmuback.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import utez.edu.mx.gmuback.kernel.enums.HttpResponse;
import utez.edu.mx.gmuback.utils.CustomResponse;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private CustomResponse cr;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomResponse> handleEndpointException(HttpServletRequest r, MethodArgumentTypeMismatchException ex) {
        Map<String, Object> e = new HashMap<>();
        e.put("path", r.getRequestURI());
        e.put("parameter", ex.getPropertyName());
        e.put("method", r.getMethod());

        cr = new CustomResponse(HttpResponse.PATH_PARAMETERS_ERROR.getMessage(), e, true, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(cr, cr.getStatus());
    }
}

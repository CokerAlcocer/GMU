package utez.edu.mx.gmuback.kernel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum HttpResponse {
    OK("Operación exitosa", HttpStatus.OK),
    CREATED("Registro exitoso", HttpStatus.CREATED),
    UPDATED("Registro actualizado exitosamente", HttpStatus.OK),
    DELETED("Registro eliminado exitosamente", HttpStatus.OK),
    BAD_REQUEST("No se pudo completar la operación", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("Ocurrió un error interno", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("Recurso no encontrado", HttpStatus.NOT_FOUND),
    FORBIDDEN("No tienes permisos para hacer esto", HttpStatus.FORBIDDEN),
    UNAUTHORIZED("Usuario sin identificación", HttpStatus.UNAUTHORIZED),
    PATH_PARAMETERS_ERROR("Los parametros de la ruta no coinciden", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}

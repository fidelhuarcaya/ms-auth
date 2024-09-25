package org.copper.auth.advice;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.copper.auth.dto.response.error.ErrorField;
import org.copper.auth.dto.response.error.ErrorResponse;
import org.copper.auth.exception.RequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class RequestExceptionController {

    /**
     * This error method  is custom
     *
     * @param e type exception
     * @return error message
     */
    @ExceptionHandler(RequestException.class)
    private ResponseEntity<ErrorResponse> runtimeExceptionHandler(RequestException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .timestamp(String.valueOf(System.currentTimeMillis()))
                .trace(Arrays.toString(e.getStackTrace()))
                .build(), HttpStatus.BAD_REQUEST);
    }


    /**
     * this method propagate if id different from integer... example baseurl/locales/null
     * correct:baseurl/locales/1
     *
     * @return error message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<ErrorResponse> runtimeExceptionHandler(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("El parámetro no puede ser nulo.")
                .trace(Arrays.toString(ex.getStackTrace()))
                .build(), HttpStatus.BAD_REQUEST);
    }

    /**
     * This method propagates the error if the body is not valid.
     *
     * @param ex is type exception
     * @return map de errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorField> runtimeExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), error.getDefaultMessage());
            }
        });
        log.error(ex.getMessage());
        return new ResponseEntity<>(ErrorField.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .fieldErrors(errors).build(), HttpStatus.BAD_REQUEST);
    }

    /**
     * This method propagates the error duplicate key value violates unique constraint.
     *
     * @param ex is type exception
     * @return map de errors
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<ErrorResponse> runtimeExceptionHandler(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.error(ex.getMessage()); ex.printStackTrace();
        String errorMessage = "Error desconocido en la base de datos.";

        // Analizar el mensaje de error para identificar detalles adicionales
        String databaseErrorMessage = ex.getCause().getMessage();
        if (databaseErrorMessage.contains("username")) {
            errorMessage = "El usuario ya existe, inicie sesión o pruebe con otro.";
        } else if (databaseErrorMessage.contains("id")) {
            errorMessage = "El id usuario ya existe.";
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .timestamp(String.valueOf(LocalDateTime.now()))
                .path(request.getRequestURI())
                .trace(Arrays.toString(ex.getStackTrace()))
                .build(), HttpStatus.BAD_REQUEST);
    }

    /**
     * This method propagates UnsupportedOperationException.
     *
     * @param ex is type exception
     * @return map de errors
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    private ResponseEntity<ErrorResponse> runtimeExceptionHandler(UnsupportedOperationException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Operación no soportado. Revise los datos enviados para generar la plantilla.")
                .trace(Arrays.toString(ex.getStackTrace()))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        String errorMessage = "Error de autenticación: Usuario o contraseña no válidos.";
        log.error(ex.getMessage()); ex.printStackTrace();
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(errorMessage)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    private ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        String errorMessage = "Inicie sesión nuevamente.";
        log.error(ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(errorMessage)
                .build(), HttpStatus.UNAUTHORIZED);
    }
}

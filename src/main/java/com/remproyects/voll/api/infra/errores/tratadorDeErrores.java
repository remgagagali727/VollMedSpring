package com.remproyects.voll.api.infra.errores;

import com.remproyects.voll.api.infra.exception.IntegrityValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class tratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
        List<DatosErrorValidation> errores = e.getFieldErrors()
                .stream().map(DatosErrorValidation::new)
                .toList();
        return ResponseEntity.badRequest()
                .body(errores);
    }

    @ExceptionHandler(IntegrityValidationException.class)
    public ResponseEntity tratarError400(IntegrityValidationException e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

    private record DatosErrorValidation(String campo, String error) {
        public DatosErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}

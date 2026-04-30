package cat.itacademy.s04.t01.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EmailAlreadyExistsException.class, UserIdDoesNotExists.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse body = new ErrorResponse(HttpStatus.CONFLICT, e.getMessage(), path);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}

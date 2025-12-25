package java19.instagramproject.exceptions.handler;

import java19.instagramproject.dto.exceptionDto.ExceptionResponse;
import java19.instagramproject.exceptions.ForbiddenException;
import java19.instagramproject.exceptions.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
        public ExceptionResponse notFound (NotFoundException notFoundException){
            return ExceptionResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .exceptionClassName(notFoundException.getClass().getName())
                    .message(notFoundException.getMessage())
                    .build();
        }
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse forbidden (ForbiddenException forbiddenException){
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .exceptionClassName("ForbiddenException")
                .message(forbiddenException.getMessage())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse accessDenied(org.springframework.security.access.AccessDeniedException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .exceptionClassName("AccessDeniedException")
                .message("Access denied")
                .build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse illegalArgument(IllegalArgumentException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .exceptionClassName("IllegalArgumentException")
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse validation(MethodArgumentNotValidException e) {

        String message = Optional.ofNullable(e.getBindingResult().getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation error");

        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .exceptionClassName("ValidationException")
                .message(message)
                .build();
    }
}

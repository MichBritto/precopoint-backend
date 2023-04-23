package br.com.precopoint.PrecoPoint.common;


import br.com.precopoint.PrecoPoint.dto.error.ErrorMessageDto;
import br.com.precopoint.PrecoPoint.exception.AlreadyExistsException;
import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        log.error(exception.getMessage(), exception);
        List<ErrorMessageDto> dto = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(
                e -> {
                    final String message = e.getDefaultMessage();
                    final ErrorMessageDto error = ErrorMessageDto.builder()
                            .errorMessage(message)
                            .field(e.getField())
                            .build();
                    dto.add(error);
                }
        );
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleNotFoundException(NotFoundException exception){
        log.error(exception.getMessage(), exception);
        ErrorMessageDto dto = ErrorMessageDto.builder().errorMessage(exception.getMessage()).build();
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDto> handleAlreadyExistsException(AlreadyExistsException exception){
        log.error(exception.getMessage(), exception);
        ErrorMessageDto dto = ErrorMessageDto.builder().errorMessage(exception.getMessage()).build();
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<ErrorMessageDto> handleDefaultException(DefaultException exception){
        log.error(exception.getMessage(), exception);
        ErrorMessageDto dto = ErrorMessageDto.builder().errorMessage(exception.getMessage()).build();
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessageDto> handleUnauthorizedException(UnauthorizedException exception){
        log.error(exception.getMessage(), exception);
        ErrorMessageDto dto = ErrorMessageDto.builder().errorMessage(exception.getMessage()).build();
        return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
    }
}

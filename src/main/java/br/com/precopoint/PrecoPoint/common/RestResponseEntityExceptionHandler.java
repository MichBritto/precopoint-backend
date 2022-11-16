package br.com.precopoint.PrecoPoint.common;


import br.com.precopoint.PrecoPoint.dto.error.ErrorMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

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
}

package br.com.precopoint.PrecoPoint.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlreadyExistsException extends RuntimeException {
    private String message;
}

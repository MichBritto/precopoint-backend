package br.com.precopoint.PrecoPoint.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultException extends RuntimeException{
    private String message;
}

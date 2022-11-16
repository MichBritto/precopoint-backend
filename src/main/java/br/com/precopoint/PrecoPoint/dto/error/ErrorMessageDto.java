package br.com.precopoint.PrecoPoint.dto.error;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
public class ErrorMessageDto {

    private String errorMessage;
    private String field;

}

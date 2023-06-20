package br.com.precopoint.PrecoPoint.dto.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggingResponseDto {
    private int eventId;
    private String user;
    private String eventDate;
    private String level;
    private String logger;
    private String msg;
    private String throwable;
}

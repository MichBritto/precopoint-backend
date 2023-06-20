package br.com.precopoint.PrecoPoint.service.impl;

import br.com.precopoint.PrecoPoint.dto.log.LoggingResponseDto;
import br.com.precopoint.PrecoPoint.model.Logging;
import br.com.precopoint.PrecoPoint.repository.LoggingRepository;
import br.com.precopoint.PrecoPoint.service.LoggingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoggingServiceImpl implements LoggingService {

    @Autowired
    LoggingRepository loggingRepository;

    @Override
    public ResponseEntity<Page<?>> getLogging(Pageable pageable) {
        ModelMapper mapper = new ModelMapper();
        Page<LoggingResponseDto> page = loggingRepository.findAllByOrderByEventDateDesc(pageable)
                .map(logging -> mapper.map(logging, LoggingResponseDto.class));
        return ResponseEntity.ok(page);
    }
}

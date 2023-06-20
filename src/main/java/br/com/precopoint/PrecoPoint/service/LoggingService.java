package br.com.precopoint.PrecoPoint.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface LoggingService {
    ResponseEntity<Page<?>> getLogging( Pageable pageable);
}

package br.com.precopoint.PrecoPoint.repository;

import br.com.precopoint.PrecoPoint.model.Logging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository extends JpaRepository<Logging, Integer> {
    Page<Logging> findAllByOrderByEventDateDesc(Pageable pageable);
}

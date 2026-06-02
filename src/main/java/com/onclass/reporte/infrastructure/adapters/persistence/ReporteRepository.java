package com.onclass.reporte.infrastructure.adapters.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReporteRepository extends ReactiveMongoRepository<ReporteDocument, String> {
    Mono<ReporteDocument> findTopByOrderByCantidadPersonasDesc();
    Mono<ReporteDocument> findByBootcampId(Long bootcampId);
}

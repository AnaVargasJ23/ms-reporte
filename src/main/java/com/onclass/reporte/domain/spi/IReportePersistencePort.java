package com.onclass.reporte.domain.spi;

import com.onclass.reporte.domain.model.ReporteBootcamp;
import reactor.core.publisher.Mono;

public interface IReportePersistencePort {
    Mono<ReporteBootcamp> guardar(ReporteBootcamp reporte);
    Mono<ReporteBootcamp> findTopByCantidadPersonasDesc();
    Mono<Void> actualizarCantidadPersonas(Long bootcampId, Integer cantidadPersonas);
}

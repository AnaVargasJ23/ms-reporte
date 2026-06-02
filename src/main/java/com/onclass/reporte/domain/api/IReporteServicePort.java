package com.onclass.reporte.domain.api;

import com.onclass.reporte.domain.model.ReporteBootcamp;
import reactor.core.publisher.Mono;

public interface IReporteServicePort {
    Mono<ReporteBootcamp> guardarReporte(ReporteBootcamp reporte);
    Mono<ReporteBootcamp> obtenerBootcampMasExitoso();
    Mono<Void> actualizarPersonas(Long bootcampId, Integer cantidadPersonas);
}

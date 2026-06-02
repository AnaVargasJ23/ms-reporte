package com.onclass.reporte.domain.usecase;

import com.onclass.reporte.domain.api.IReporteServicePort;
import com.onclass.reporte.domain.constants.ReporteConstants;
import com.onclass.reporte.domain.enums.ReporteErrorEnum;
import com.onclass.reporte.domain.excepcion.ReporteException;
import com.onclass.reporte.domain.model.ReporteBootcamp;
import com.onclass.reporte.domain.spi.IReportePersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ReporteUseCase implements IReporteServicePort {

    private final IReportePersistencePort persistencePort;

    @Override
    public Mono<ReporteBootcamp> guardarReporte(ReporteBootcamp reporte) {
        if (reporte.getCantidadPersonas() == null) {
            reporte.setCantidadPersonas(ReporteConstants.CANTIDAD_PERSONAS_INICIAL);
        }
        return persistencePort.guardar(reporte);
    }

    @Override
    public Mono<ReporteBootcamp> obtenerBootcampMasExitoso() {
        return persistencePort.findTopByCantidadPersonasDesc()
                .switchIfEmpty(Mono.error(new ReporteException(
                        ReporteErrorEnum.REPORTE_NO_ENCONTRADO.getCode(),
                        ReporteErrorEnum.REPORTE_NO_ENCONTRADO.getMessage())));
    }

    @Override
    public Mono<Void> actualizarPersonas(Long bootcampId, Integer cantidadPersonas) {
        return persistencePort.actualizarCantidadPersonas(bootcampId, cantidadPersonas);
    }
}

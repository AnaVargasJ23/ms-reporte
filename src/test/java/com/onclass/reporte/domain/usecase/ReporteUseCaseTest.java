package com.onclass.reporte.domain.usecase;

import com.onclass.reporte.domain.excepcion.ReporteException;
import com.onclass.reporte.domain.model.ReporteBootcamp;
import com.onclass.reporte.domain.spi.IReportePersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReporteUseCaseTest {

    @Mock
    private IReportePersistencePort persistencePort;

    @InjectMocks
    private ReporteUseCase useCase;

    private ReporteBootcamp reporteValido() {
        ReporteBootcamp reporte = new ReporteBootcamp();
        reporte.setBootcampId(1L);
        reporte.setNombre("Bootcamp Java");
        reporte.setDescripcion("Descripción válida");
        reporte.setFechaLanzamiento(LocalDate.of(2026, 7, 1));
        reporte.setDuracion(90);
        reporte.setCapacidades(List.of());
        reporte.setCantidadCapacidades(2);
        reporte.setCantidadTecnologias(6);
        reporte.setCantidadPersonas(10);
        return reporte;
    }

    @Test
    void guardarReporte_exitoso() {
        ReporteBootcamp reporte = reporteValido();
        reporte.setId("abc123");
        when(persistencePort.guardar(any())).thenReturn(Mono.just(reporte));

        StepVerifier.create(useCase.guardarReporte(reporteValido()))
                .expectNextMatches(r -> r.getNombre().equals("Bootcamp Java"))
                .verifyComplete();
    }

    @Test
    void guardarReporte_cantidadPersonasNull_usaDefault() {
        ReporteBootcamp reporte = reporteValido();
        reporte.setCantidadPersonas(null);
        reporte.setId("abc123");
        when(persistencePort.guardar(any())).thenReturn(Mono.just(reporte));

        StepVerifier.create(useCase.guardarReporte(reporte))
                .expectNextMatches(r -> r.getNombre().equals("Bootcamp Java"))
                .verifyComplete();
    }

    @Test
    void obtenerBootcampMasExitoso_exitoso() {
        ReporteBootcamp reporte = reporteValido();
        reporte.setId("abc123");
        when(persistencePort.findTopByCantidadPersonasDesc()).thenReturn(Mono.just(reporte));

        StepVerifier.create(useCase.obtenerBootcampMasExitoso())
                .expectNextMatches(r -> r.getCantidadPersonas() == 10)
                .verifyComplete();
    }

    @Test
    void obtenerBootcampMasExitoso_sinReportes_lanzaError() {
        when(persistencePort.findTopByCantidadPersonasDesc()).thenReturn(Mono.empty());

        StepVerifier.create(useCase.obtenerBootcampMasExitoso())
                .expectError(ReporteException.class)
                .verify();
    }

    @Test
    void actualizarPersonas_exitoso() {
        when(persistencePort.actualizarCantidadPersonas(1L, 5)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.actualizarPersonas(1L, 5))
                .verifyComplete();
    }

    @Test
    void actualizarPersonas_errorPersistencia_propaga() {
        when(persistencePort.actualizarCantidadPersonas(eq(1L), eq(5)))
                .thenReturn(Mono.error(new RuntimeException("Error de BD")));

        StepVerifier.create(useCase.actualizarPersonas(1L, 5))
                .expectError(RuntimeException.class)
                .verify();
    }
}

package com.onclass.reporte.infrastructure.entrypoints.handler;

import com.onclass.reporte.domain.api.IReporteServicePort;
import com.onclass.reporte.domain.excepcion.ReporteException;
import com.onclass.reporte.domain.model.ReporteBootcamp;
import com.onclass.reporte.infrastructure.entrypoints.dto.ReporteRegistradoResponse;
import com.onclass.reporte.infrastructure.entrypoints.dto.ReporteRequest;
import com.onclass.reporte.infrastructure.entrypoints.util.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReporteHandler {

    private final IReporteServicePort reporteServicePort;

    public Mono<ServerResponse> guardarReporte(ServerRequest request) {
        return request.bodyToMono(ReporteRequest.class)
                .flatMap(req -> {
                    ReporteBootcamp reporte = new ReporteBootcamp();
                    reporte.setBootcampId(req.getBootcampId());
                    reporte.setNombre(req.getNombre());
                    reporte.setDescripcion(req.getDescripcion());
                    reporte.setFechaLanzamiento(req.getFechaLanzamiento());
                    reporte.setDuracion(req.getDuracion());
                    reporte.setCapacidades(req.getCapacidades());
                    reporte.setCantidadCapacidades(req.getCantidadCapacidades());
                    reporte.setCantidadTecnologias(req.getCantidadTecnologias());
                    reporte.setCantidadPersonas(req.getCantidadPersonas());
                    return reporteServicePort.guardarReporte(reporte);
                })
                .flatMap(saved -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(ReporteRegistradoResponse.builder()
                                .id(saved.getId())
                                .bootcampId(saved.getBootcampId())
                                .nombre(saved.getNombre())
                                .mensaje("Reporte guardado exitosamente")
                                .build()))
                .onErrorResume(ReporteException.class, e -> {
                    log.error("Error de negocio: {}", e.getMessage());
                    return ServerResponse
                            .status(HttpStatus.BAD_REQUEST)
                            .bodyValue(ErrorDTO.builder()
                                    .code(e.getCode())
                                    .message(e.getMessage())
                                    .build());
                })
                .onErrorResume(Exception.class, e -> {
                    log.error("Error guardando reporte: {}", e.getMessage());
                    return ServerResponse
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .bodyValue(ErrorDTO.builder()
                                    .code("REP-500")
                                    .message("Error guardando reporte")
                                    .build());
                });
    }

    public Mono<ServerResponse> obtenerBootcampMasExitoso(ServerRequest request) {
        return reporteServicePort.obtenerBootcampMasExitoso()
                .flatMap(reporte -> ServerResponse.ok().bodyValue(reporte))
                .onErrorResume(ReporteException.class, e -> {
                    log.error("Error de negocio: {}", e.getMessage());
                    return ServerResponse
                            .status(HttpStatus.NOT_FOUND)
                            .bodyValue(ErrorDTO.builder()
                                    .code(e.getCode())
                                    .message(e.getMessage())
                                    .build());
                })
                .onErrorResume(Exception.class, e -> {
                    log.error("Error obteniendo reporte: {}", e.getMessage());
                    return ServerResponse
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .bodyValue(ErrorDTO.builder()
                                    .code("REP-500")
                                    .message("Error obteniendo reporte")
                                    .build());
                });
    }

    public Mono<ServerResponse> actualizarPersonas(ServerRequest request) {
        Long bootcampId = Long.valueOf(request.pathVariable("bootcampId"));
        Integer cantidad = Integer.valueOf(request.pathVariable("cantidad"));
        return reporteServicePort.actualizarPersonas(bootcampId, cantidad)
                .then(ServerResponse.ok()
                        .bodyValue(ErrorDTO.builder()
                                .code("REP-200")
                                .message("Cantidad de personas actualizada exitosamente")
                                .build()))
                .onErrorResume(Exception.class, e -> {
                    log.error("Error actualizando personas: {}", e.getMessage());
                    return ServerResponse
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .bodyValue(ErrorDTO.builder()
                                    .code("REP-500")
                                    .message("Error actualizando reporte")
                                    .build());
                });
    }
}

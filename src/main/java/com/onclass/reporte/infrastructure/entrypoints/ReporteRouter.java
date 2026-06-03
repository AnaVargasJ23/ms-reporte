package com.onclass.reporte.infrastructure.entrypoints;

import com.onclass.reporte.infrastructure.entrypoints.dto.ReporteRequest;
import com.onclass.reporte.infrastructure.entrypoints.handler.ReporteHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Tag(name = "Reporte", description = "Gestión de reportes On-Class")
public class ReporteRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/reportes",
                    method = RequestMethod.POST,
                    beanClass = ReporteHandler.class,
                    beanMethod = "guardarReporte",
                    operation = @Operation(
                            operationId = "guardarReporte",
                            summary = "Guardar reporte de bootcamp",
                            tags = {"Reporte"},
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ReporteRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Reporte guardado exitosamente"),
                                    @ApiResponse(responseCode = "400", description = "Error de negocio"),
                                    @ApiResponse(responseCode = "500", description = "Error interno")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/reportes/bootcamp-exitoso",
                    method = RequestMethod.GET,
                    beanClass = ReporteHandler.class,
                    beanMethod = "obtenerBootcampMasExitoso",
                    operation = @Operation(
                            operationId = "obtenerBootcampMasExitoso",
                            summary = "Obtener bootcamp con mayor cantidad de personas",
                            tags = {"Reporte"},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Bootcamp más exitoso"),
                                    @ApiResponse(responseCode = "404", description = "No hay reportes")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/reportes/{bootcampId}/personas/{cantidad}",
                    method = RequestMethod.PUT,
                    beanClass = ReporteHandler.class,
                    beanMethod = "actualizarPersonas",
                    operation = @Operation(
                            operationId = "actualizarPersonas",
                            summary = "Actualizar cantidad de personas de un bootcamp",
                            tags = {"Reporte"},
                            parameters = {
                                    @io.swagger.v3.oas.annotations.Parameter(
                                            name = "bootcampId",
                                            in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
                                            required = true,
                                            description = "ID del bootcamp"
                                    ),
                                    @io.swagger.v3.oas.annotations.Parameter(
                                            name = "cantidad",
                                            in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
                                            required = true,
                                            description = "Cantidad de personas inscritas"
                                    )
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Reporte actualizado"),
                                    @ApiResponse(responseCode = "500", description = "Error interno")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> reporteRoutes(ReporteHandler handler) {
        return RouterFunctions.route()
                .POST("/api/v1/reportes", handler::guardarReporte)
                .GET("/api/v1/reportes/bootcamp-exitoso", handler::obtenerBootcampMasExitoso)
                .PUT("/api/v1/reportes/{bootcampId}/personas/{cantidad}", handler::actualizarPersonas)
                .build();
    }
}

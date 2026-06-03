package com.onclass.reporte.infrastructure.entrypoints.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteRegistradoResponse {
    private String id;
    private Long bootcampId;
    private String nombre;
    private String mensaje;
}

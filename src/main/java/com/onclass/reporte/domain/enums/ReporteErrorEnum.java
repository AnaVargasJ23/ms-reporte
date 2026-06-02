package com.onclass.reporte.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReporteErrorEnum {
    REPORTE_NO_ENCONTRADO("REP-001", "Reporte no encontrado"),
    REPORTE_YA_EXISTE("REP-002", "Ya existe un reporte para este bootcamp"),
    ERROR_INTERNO("REP-500", "Error interno del servidor");

    private final String code;
    private final String message;
}

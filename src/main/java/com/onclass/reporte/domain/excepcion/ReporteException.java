package com.onclass.reporte.domain.excepcion;

import lombok.Getter;

@Getter
public class ReporteException extends RuntimeException {
    private final String code;

    public ReporteException(String code, String message) {
        super(message);
        this.code = code;
    }
}

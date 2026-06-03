package com.onclass.reporte.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CapacidadReporte {
    private Long id;
    private String nombre;
    private List<TecnologiaReporte> tecnologias;
}

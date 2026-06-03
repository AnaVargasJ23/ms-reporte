package com.onclass.reporte.infrastructure.entrypoints.dto;

import com.onclass.reporte.domain.model.CapacidadReporte;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteRequest {
    private Long bootcampId;
    private String nombre;
    private String descripcion;
    private LocalDate fechaLanzamiento;
    private Integer duracion;
    private List<CapacidadReporte> capacidades;
    private Integer cantidadCapacidades;
    private Integer cantidadTecnologias;
    private Integer cantidadPersonas;
}

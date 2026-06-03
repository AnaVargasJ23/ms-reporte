package com.onclass.reporte.infrastructure.adapters.persistence;

import com.onclass.reporte.domain.model.CapacidadReporte;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "reportes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDocument {
    @Id
    private String id;
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

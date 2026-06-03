package com.onclass.reporte.infrastructure.adapters.persistence;

import com.onclass.reporte.domain.model.ReporteBootcamp;
import com.onclass.reporte.domain.spi.IReportePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReportePersistenceAdapter implements IReportePersistencePort {

    private final ReporteRepository repository;

    @Override
    public Mono<ReporteBootcamp> guardar(ReporteBootcamp reporte) {
        return repository.save(toDocument(reporte)).map(this::toDomain);
    }

    @Override
    public Mono<ReporteBootcamp> findTopByCantidadPersonasDesc() {
        return repository.findTopByOrderByCantidadPersonasDesc().map(this::toDomain);
    }

    @Override
    public Mono<Void> actualizarCantidadPersonas(Long bootcampId, Integer cantidadPersonas) {
        return repository.findByBootcampId(bootcampId)
                .flatMap(doc -> {
                    doc.setCantidadPersonas(cantidadPersonas);
                    return repository.save(doc);
                })
                .then();
    }

    private ReporteDocument toDocument(ReporteBootcamp reporte) {
        ReporteDocument doc = new ReporteDocument();
        doc.setId(reporte.getId());
        doc.setBootcampId(reporte.getBootcampId());
        doc.setNombre(reporte.getNombre());
        doc.setDescripcion(reporte.getDescripcion());
        doc.setFechaLanzamiento(reporte.getFechaLanzamiento());
        doc.setDuracion(reporte.getDuracion());
        doc.setCapacidades(reporte.getCapacidades());
        doc.setCantidadCapacidades(reporte.getCantidadCapacidades());
        doc.setCantidadTecnologias(reporte.getCantidadTecnologias());
        doc.setCantidadPersonas(reporte.getCantidadPersonas());
        return doc;
    }

    private ReporteBootcamp toDomain(ReporteDocument doc) {
        ReporteBootcamp reporte = new ReporteBootcamp();
        reporte.setId(doc.getId());
        reporte.setBootcampId(doc.getBootcampId());
        reporte.setNombre(doc.getNombre());
        reporte.setDescripcion(doc.getDescripcion());
        reporte.setFechaLanzamiento(doc.getFechaLanzamiento());
        reporte.setDuracion(doc.getDuracion());
        reporte.setCapacidades(doc.getCapacidades());
        reporte.setCantidadCapacidades(doc.getCantidadCapacidades());
        reporte.setCantidadTecnologias(doc.getCantidadTecnologias());
        reporte.setCantidadPersonas(doc.getCantidadPersonas());
        return reporte;
    }
}

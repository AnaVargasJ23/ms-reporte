package com.onclass.reporte.infrastructure.configuration;

import com.onclass.reporte.domain.api.IReporteServicePort;
import com.onclass.reporte.domain.spi.IReportePersistencePort;
import com.onclass.reporte.domain.usecase.ReporteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IReporteServicePort reporteServicePort(IReportePersistencePort persistencePort) {
        return new ReporteUseCase(persistencePort);
    }
}

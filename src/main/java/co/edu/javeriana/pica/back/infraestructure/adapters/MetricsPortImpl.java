package co.edu.javeriana.pica.back.infraestructure.adapters;

import co.edu.javeriana.pica.back.core.interfaces.MetricsPort;
import io.micrometer.core.instrument.MeterRegistry;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MetricsPortImpl implements MetricsPort {

    private final MeterRegistry registry;

    public MetricsPortImpl(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void incrementCounter(String metric) {
        registry.counter(metric).increment();
    }
}

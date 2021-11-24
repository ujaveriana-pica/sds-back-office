package co.edu.javeriana.pica.back.core.interfaces;

public interface MetricsPort {

    String TRAMITES_APROBADOS = "app.frontoffice.tramites.aprobados";
    String TRAMITES_DESAPROBADOS = "app.frontoffice.tramites.desaprobados";
    String TRAMITES_GENERADOS = "app.frontoffice.tramites.generados";

    void incrementCounter(String metric);
}

package co.edu.javeriana.pica.back.core.interfaces;

public interface MetricsPort {

    String TRAMITES_APROBADOS = "app.frontoffice.tramites.aprobados";
    String TRAMITES_DESAPROBADOS = "app.frontoffice.tramites.desaprobados";
    String TRAMITES_APROBADOS_GENERADOS = "app.frontoffice.tramites.aprobados.generados";
    String TRAMITES_DESAPROBADOS_GENERADOS = "app.frontoffice.tramites.desaprobados.generados";

    void incrementCounter(String metric);
}

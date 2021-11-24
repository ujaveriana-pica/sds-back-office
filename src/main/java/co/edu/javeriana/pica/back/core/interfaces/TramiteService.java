package co.edu.javeriana.pica.back.core.interfaces;

import co.edu.javeriana.pica.back.core.entities.Tramite;

public interface TramiteService {

    String ESTADO_RADICADO = "radicado";
    String ESTADO_APROBADO = "aprobado";
    String ESTADO_DESAPROBADO = "desaprobado";
    String ESTADO_APROBADO_GENERADO = "aprobado-generado";
    String ESTADO_DESAPROBADO_GENERADO = "desaprobado-generado";

    void procesar(Tramite tramite);
}

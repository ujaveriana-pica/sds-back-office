package co.edu.javeriana.pica.back.core.interfaces;

import co.edu.javeriana.pica.back.core.entities.Tramite;

public interface TramiteService {

    String ESTADO_RADICADO = "radicado";
    String ESTADO_APROBADO = "aprobado";
    String ESTADO_DESAPROBADO = "desaprobado";
    String ESTADO_GENERADO = "generado";

    void procesar(Tramite tramite);
}

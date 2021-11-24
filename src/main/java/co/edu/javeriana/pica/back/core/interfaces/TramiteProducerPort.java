package co.edu.javeriana.pica.back.core.interfaces;

import co.edu.javeriana.pica.back.core.entities.Tramite;

public interface TramiteProducerPort {
    void send(Tramite tramite);
}

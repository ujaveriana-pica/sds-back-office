package co.edu.javeriana.pica.back.infraestructure.adapters;

import co.edu.javeriana.pica.back.core.entities.Tramite;
import co.edu.javeriana.pica.back.core.interfaces.TramiteProducerPort;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import javax.inject.Inject;

public class TramiteProducerPortImpl implements TramiteProducerPort  {

    @Inject
    @Channel("tramites-out")
    Emitter<Tramite> tramiteEmitter;

    @Override
    public void send(Tramite tramite) {
        tramiteEmitter.send(tramite);
    }
}

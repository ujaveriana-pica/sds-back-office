package co.edu.javeriana.pica.back.infraestructure.consumer;

import co.edu.javeriana.pica.back.core.entities.Tramite;
import co.edu.javeriana.pica.back.core.interfaces.TramiteService;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class TramitesConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(TramitesConsumer.class);
    private final TramiteService tramiteService;

    public TramitesConsumer(TramiteService tramiteService) {
        this.tramiteService = tramiteService;
    }

    @Incoming("tramites-in")
    @Blocking
    public CompletionStage<Void> consume(Message<Tramite> msg) {
        Tramite tramite = msg.getPayload();
        if (tramite != null) {
            tramiteService.procesar(tramite);
        } else {
            LOG.warn("Mensaje invalido, se descarta el mensaje de la cola");
        }
        return msg.ack();
    }
}

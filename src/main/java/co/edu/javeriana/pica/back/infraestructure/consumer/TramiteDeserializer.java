package co.edu.javeriana.pica.back.infraestructure.consumer;

import co.edu.javeriana.pica.back.core.entities.Tramite;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class TramiteDeserializer extends ObjectMapperDeserializer<Tramite> {
    public TramiteDeserializer() {
        super(Tramite.class);
    }
}

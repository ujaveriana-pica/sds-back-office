package co.edu.javeriana.pica.back.infraestructure.adapters;

import co.edu.javeriana.pica.back.core.entities.Notificacion;
import co.edu.javeriana.pica.back.core.interfaces.NotificacionPort;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class NotificacionPortImpl implements NotificacionPort {

    @Inject
    @Channel("notifications")
    Emitter<Notificacion> notificacionEmitter;

    @Override
    public void send(Notificacion notificacion) {
        notificacionEmitter.send(notificacion);
    }
}

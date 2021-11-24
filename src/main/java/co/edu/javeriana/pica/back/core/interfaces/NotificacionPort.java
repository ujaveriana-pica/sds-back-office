package co.edu.javeriana.pica.back.core.interfaces;

import co.edu.javeriana.pica.back.core.entities.Notificacion;

public interface NotificacionPort {
    void send(Notificacion notificacion);
}


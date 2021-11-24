package co.edu.javeriana.pica.back.core.services;

import co.edu.javeriana.pica.back.core.entities.Notificacion;
import co.edu.javeriana.pica.back.core.entities.Tramite;
import co.edu.javeriana.pica.back.core.interfaces.MetricsPort;
import co.edu.javeriana.pica.back.core.interfaces.NotificacionPort;
import co.edu.javeriana.pica.back.core.interfaces.TramiteProducerPort;
import co.edu.javeriana.pica.back.core.interfaces.TramiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@ApplicationScoped
public class TramiteServiceImpl implements TramiteService {

    private static final Logger LOG = LoggerFactory.getLogger(TramiteServiceImpl.class);
    private final MetricsPort metricsPort;
    private final TramiteProducerPort tramiteProducerPort;
    private final NotificacionPort notificacionPort;
    private static final int MIN_RANDOM = 0;
    private static final int MAX_RANDOM = 10;
    private static final int MAX_PERCENTAGE = 7;
    private static final int SLEEP = 30 * 1000;

    public TramiteServiceImpl(MetricsPort metricsPort, TramiteProducerPort tramiteProducerPort,
                              NotificacionPort notificacionPort) {
        this.metricsPort = metricsPort;
        this.tramiteProducerPort = tramiteProducerPort;
        this.notificacionPort = notificacionPort;
    }
    @Override
    public void procesar(Tramite tramite) {
        try {
            if (ESTADO_RADICADO.equalsIgnoreCase(tramite.getEstado())) {
                LOG.info("Procesando tramite con ID {}", tramite.getId());
                Thread.sleep(SLEEP);
                int rand = getRandomNumberInRange(MIN_RANDOM, MAX_RANDOM);
                if (rand <= MAX_PERCENTAGE) {
                    tramite.setEstado(ESTADO_APROBADO);
                    tramiteProducerPort.send(tramite);
                    metricsPort.incrementCounter(MetricsPort.TRAMITES_APROBADOS);
                    LOG.info("Tramite con ID {} aprobado", tramite.getId());
                } else {
                    tramite.setEstado(ESTADO_DESAPROBADO);
                    tramiteProducerPort.send(tramite);
                    metricsPort.incrementCounter(MetricsPort.TRAMITES_DESAPROBADOS);
                    LOG.info("Tramite con ID {} desaprobado", tramite.getId());
                }
            } else if (ESTADO_APROBADO_GENERADO.equalsIgnoreCase(tramite.getEstado())) {
                Notificacion notificacion = getNotificacion(tramite);
                if (notificacion != null) {
                    notificacionPort.send(notificacion);
                    metricsPort.incrementCounter(MetricsPort.TRAMITES_APROBADOS_GENERADOS);
                    LOG.info("Se envia notificacion de resolucion de tramite aprobado con ID {}", tramite.getId());
                }
            } else if (ESTADO_DESAPROBADO_GENERADO.equalsIgnoreCase(tramite.getEstado())) {
                Notificacion notificacion = getNotificacion(tramite);
                if (notificacion != null) {
                    notificacionPort.send(notificacion);
                    metricsPort.incrementCounter(MetricsPort.TRAMITES_DESAPROBADOS_GENERADOS);
                    LOG.info("Se envia notificacion de resolucion de tramite desaprobado con ID {}", tramite.getId());
                }
            } else {
                LOG.info("Mensaje de tramite con ID {} descartado por tener estado {}",
                        tramite.getId(), tramite.getEstado());
            }
        } catch (Exception e) {
            LOG.warn(e.getMessage());
        }
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private Notificacion getNotificacion(Tramite tramite) {
        if (tramite.getCreador() != null) {
            Notificacion notificacion = new Notificacion();
            if (ESTADO_APROBADO_GENERADO.equalsIgnoreCase(tramite.getEstado())) {
                notificacion.setTemplate("tramite-aprobado-generado");
            } else if (ESTADO_DESAPROBADO_GENERADO.equalsIgnoreCase(tramite.getEstado())) {
                notificacion.setTemplate("tramite-desaprobado-generado");
            } else {
               LOG.warn("No se puede crear notificacion para el tramite con id {}, estado invalido {} ",
                       tramite.getId(), tramite.getEstado());
               return null;
            }
            notificacion.setTo(tramite.getCreador().getEmail());
            Map<String, String> vars = new HashMap<>();
            String name = tramite.getCreador().getName() != null ? tramite.getCreador().getName() : "";
            String lastName = tramite.getCreador().getLastName() != null
                    ? tramite.getCreador().getLastName() : "";
            vars.put("nombre", name + " " + lastName);
            vars.put("tramiteId", tramite.getId());
            notificacion.setVars(vars);
            return notificacion;
        } else {
            LOG.warn("El tramite con id {} no tiene creador", tramite.getId());
            return null;
        }
    }
}

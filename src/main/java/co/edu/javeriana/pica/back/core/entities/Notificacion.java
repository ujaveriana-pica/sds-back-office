package co.edu.javeriana.pica.back.core.entities;

import lombok.Data;
import java.util.Map;

@Data
public class Notificacion {
    private String to;
    private String template;
    private Map<String, String> vars;
}

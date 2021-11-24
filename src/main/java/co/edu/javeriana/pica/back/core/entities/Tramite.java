package co.edu.javeriana.pica.back.core.entities;

import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class Tramite {
    private String id;
    private Map<String, String> data;
    private String tipo;
    private String estado;
    private List<Adjunto> adjuntos = new ArrayList<>();
    private User creador;
    private Date fechaCreacion;
    private Date fechaRadicacion;

}

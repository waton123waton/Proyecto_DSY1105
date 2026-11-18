package cl.corpaliv.msvinculacion.controller;

import cl.corpaliv.msvinculacion.model.SolicitudContacto;
import cl.corpaliv.msvinculacion.repository.SolicitudContactoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudContactoController {

    private final SolicitudContactoRepository repository;

    public SolicitudContactoController(SolicitudContactoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SolicitudContacto> listar() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "fechaCreacion"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudContacto crear(@RequestBody Map<String, String> datos) {
        String nombre = limpiar(datos.get("nombre"));
        String correo = limpiar(datos.get("correo"));
        String tipo = limpiar(datos.get("tipoColaboracion"));
        String mensaje = limpiar(datos.get("mensaje"));

        if (nombre.length() < 3 || nombre.length() > 150) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre no valido");
        }
        if (correo.length() > 254 || !correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo no valido");
        }
        if (tipo.isBlank() || tipo.length() > 150) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de colaboracion no valido");
        }
        if (mensaje.length() < 10 || mensaje.length() > 2000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mensaje no valido");
        }

        SolicitudContacto solicitud = new SolicitudContacto();
        solicitud.setNombre(nombre);
        solicitud.setCorreo(correo.toLowerCase(java.util.Locale.ROOT));
        solicitud.setTipoColaboracion(tipo);
        solicitud.setMensaje(mensaje);
        return repository.save(solicitud);
    }

    @PatchMapping("/{id}/estado")
    public SolicitudContacto actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> datos) {
        String estado = datos.get("estado");
        if (estado == null || !List.of("Pendiente", "En revisión", "Resuelta").contains(estado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado no valido");
        }
        SolicitudContacto solicitud = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));
        solicitud.setEstado(estado);
        return repository.save(solicitud);
    }

    private static String limpiar(String valor) {
        return valor == null ? "" : valor.trim();
    }
}

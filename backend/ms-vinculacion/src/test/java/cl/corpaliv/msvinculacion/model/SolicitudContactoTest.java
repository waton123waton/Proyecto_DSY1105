package cl.corpaliv.msvinculacion.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudContactoTest {

    @Test
    void asignaFechaYEstadoPorDefecto() {
        var solicitud = new SolicitudContacto();
        solicitud.antesDeGuardar();
        assertNotNull(solicitud.getFechaCreacion());
        assertEquals("Pendiente", solicitud.getEstado());
    }

    @Test
    void conservaFechaYEstadoExistentes() {
        var solicitud = new SolicitudContacto();
        var fecha = LocalDateTime.of(2026, 10, 10, 9, 30);
        solicitud.setFechaCreacion(fecha);
        solicitud.setEstado("En revisión");
        solicitud.antesDeGuardar();
        assertEquals(fecha, solicitud.getFechaCreacion());
        assertEquals("En revisión", solicitud.getEstado());
    }

    @Test
    void completaTodosLosCampos() {
        var solicitud = new SolicitudContacto();
        var fecha = LocalDateTime.of(2026, 10, 10, 9, 30);
        solicitud.setId(8L);
        solicitud.setNombre("Persona de prueba");
        solicitud.setCorreo("persona@example.com");
        solicitud.setTipoColaboracion("Voluntariado");
        solicitud.setMensaje("Mensaje de solicitud de prueba");
        solicitud.setFechaCreacion(fecha);
        solicitud.setEstado("Resuelta");

        assertEquals(8L, solicitud.getId());
        assertEquals("Persona de prueba", solicitud.getNombre());
        assertEquals("persona@example.com", solicitud.getCorreo());
        assertEquals("Voluntariado", solicitud.getTipoColaboracion());
        assertEquals("Mensaje de solicitud de prueba", solicitud.getMensaje());
        assertEquals(fecha, solicitud.getFechaCreacion());
        assertEquals("Resuelta", solicitud.getEstado());
    }

    @Test
    void sustituyeEstadoEnBlancoPorPendiente() {
        var solicitud = new SolicitudContacto();
        solicitud.setEstado(" ");
        solicitud.antesDeGuardar();
        assertEquals("Pendiente", solicitud.getEstado());
    }
}

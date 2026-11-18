package cl.corpaliv.msvinculacion.controller;

import cl.corpaliv.msvinculacion.model.SolicitudContacto;
import cl.corpaliv.msvinculacion.repository.SolicitudContactoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SolicitudContactoControllerExtraTest {

    private SolicitudContactoController controlador(SolicitudContactoRepository repo) {
        return new SolicitudContactoController(repo);
    }

    private Map<String, String> datos(String nombre, String correo, String tipo, String mensaje) {
        return Map.of("nombre", nombre, "correo", correo,
                "tipoColaboracion", tipo, "mensaje", mensaje);
    }

    @Test
    void rechazaNombreCorto() {
        var repo = mock(SolicitudContactoRepository.class);
        assertThrows(ResponseStatusException.class, () -> controlador(repo).crear(
                datos("A", "ana@example.com", "Voluntariado", "Quiero participar en el proyecto")));
        verifyNoInteractions(repo);
    }

    @Test
    void rechazaNombreExtenso() {
        var repo = mock(SolicitudContactoRepository.class);
        assertThrows(ResponseStatusException.class, () -> controlador(repo).crear(
                datos("X".repeat(151), "ana@example.com", "Voluntariado", "Quiero participar en el proyecto")));
    }

    @Test
    void rechazaTipoVacio() {
        var repo = mock(SolicitudContactoRepository.class);
        assertThrows(ResponseStatusException.class, () -> controlador(repo).crear(
                datos("Ana Perez", "ana@example.com", " ", "Quiero participar en el proyecto")));
    }

    @Test
    void rechazaTipoExtenso() {
        var repo = mock(SolicitudContactoRepository.class);
        assertThrows(ResponseStatusException.class, () -> controlador(repo).crear(
                datos("Ana Perez", "ana@example.com", "T".repeat(151), "Quiero participar en el proyecto")));
    }

    @Test
    void rechazaMensajeExtenso() {
        var repo = mock(SolicitudContactoRepository.class);
        assertThrows(ResponseStatusException.class, () -> controlador(repo).crear(
                datos("Ana Perez", "ana@example.com", "Voluntariado", "M".repeat(2001))));
    }

    @Test
    void rechazaCorreoExtenso() {
        var repo = mock(SolicitudContactoRepository.class);
        assertThrows(ResponseStatusException.class, () -> controlador(repo).crear(
                datos("Ana Perez", "a".repeat(255) + "@example.com", "Voluntariado", "Mensaje valido de solicitud")));
    }

    @Test
    void guardaLosDatosLimpios() {
        var repo = mock(SolicitudContactoRepository.class);
        when(repo.save(any(SolicitudContacto.class))).thenAnswer(inv -> inv.getArgument(0));
        var guardada = controlador(repo).crear(
                datos("  Ana Perez  ", "  ANA@EXAMPLE.COM  ", "  Voluntariado  ", "  Quiero ayudar  "));
        assertEquals("Ana Perez", guardada.getNombre());
        assertEquals("ana@example.com", guardada.getCorreo());
        assertEquals("Voluntariado", guardada.getTipoColaboracion());
        assertEquals("Quiero ayudar", guardada.getMensaje());
    }

    @Test
    void rechazaEstadoAusente() {
        var repo = mock(SolicitudContactoRepository.class);
        assertThrows(ResponseStatusException.class,
                () -> controlador(repo).actualizarEstado(1L, Map.of()));
        verifyNoInteractions(repo);
    }

    @Test
    void cambiaElEstadoAEnRevision() {
        var repo = mock(SolicitudContactoRepository.class);
        var solicitud = new SolicitudContacto();
        solicitud.setEstado("Pendiente");
        when(repo.findById(1L)).thenReturn(Optional.of(solicitud));
        when(repo.save(any(SolicitudContacto.class))).thenAnswer(inv -> inv.getArgument(0));
        var resultado = controlador(repo).actualizarEstado(1L, Map.of("estado", "En revisión"));
        assertEquals("En revisión", resultado.getEstado());
        verify(repo).save(solicitud);
    }
}

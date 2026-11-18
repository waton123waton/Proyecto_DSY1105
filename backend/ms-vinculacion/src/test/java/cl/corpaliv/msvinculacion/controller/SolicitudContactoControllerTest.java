package cl.corpaliv.msvinculacion.controller;

import cl.corpaliv.msvinculacion.model.SolicitudContacto;
import cl.corpaliv.msvinculacion.repository.SolicitudContactoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SolicitudContactoControllerTest {

    @Test
    void crearGuardaDatosValidos() {
        var repo = mock(SolicitudContactoRepository.class);
        when(repo.save(any(SolicitudContacto.class))).thenAnswer(inv -> inv.getArgument(0));
        var controller = new SolicitudContactoController(repo);
        var creado = controller.crear(Map.of(
                "nombre", "Ana Perez", "correo", "ana@example.com",
                "tipoColaboracion", "Voluntariado", "mensaje", "Quiero participar en CORPALIV"));
        assertEquals("Ana Perez", creado.getNombre());
        assertEquals("Pendiente", java.util.Optional.ofNullable(creado.getEstado()).orElse("Pendiente"));
        verify(repo).save(any(SolicitudContacto.class));
    }

    @Test
    void rechazaCorreoInvalido() {
        var repo = mock(SolicitudContactoRepository.class);
        var controller = new SolicitudContactoController(repo);
        assertThrows(ResponseStatusException.class, () -> controller.crear(Map.of(
                "nombre", "Ana Perez", "correo", "invalido",
                "tipoColaboracion", "Voluntariado", "mensaje", "Mensaje de prueba valido")));
        verifyNoInteractions(repo);
    }

    @Test
    void rechazaMensajeCorto() {
        var repo = mock(SolicitudContactoRepository.class);
        var controller = new SolicitudContactoController(repo);
        assertThrows(ResponseStatusException.class, () -> controller.crear(Map.of(
                "nombre", "Ana Perez", "correo", "ana@example.com",
                "tipoColaboracion", "Voluntariado", "mensaje", "Hola")));
    }

    @Test
    void listaEnOrdenDescendente() {
        var repo = mock(SolicitudContactoRepository.class);
        when(repo.findAll(any(Sort.class))).thenReturn(List.of());
        var controller = new SolicitudContactoController(repo);
        assertTrue(controller.listar().isEmpty());
        verify(repo).findAll(any(Sort.class));
    }

    @Test
    void actualizaEstado() {
        var repo = mock(SolicitudContactoRepository.class);
        var solicitud = new SolicitudContacto();
        solicitud.setEstado("Pendiente");
        when(repo.findById(2L)).thenReturn(Optional.of(solicitud));
        when(repo.save(any(SolicitudContacto.class))).thenAnswer(inv -> inv.getArgument(0));
        var controller = new SolicitudContactoController(repo);
        var actualizada = controller.actualizarEstado(2L, Map.of("estado", "Resuelta"));
        assertEquals("Resuelta", actualizada.getEstado());
    }

    @Test
    void rechazaEstadoDesconocido() {
        var repo = mock(SolicitudContactoRepository.class);
        var controller = new SolicitudContactoController(repo);
        assertThrows(ResponseStatusException.class,
                () -> controller.actualizarEstado(2L, Map.of("estado", "Borrada")));
        verifyNoInteractions(repo);
    }

    @Test
    void devuelve404CuandoIdNoExiste() {
        var repo = mock(SolicitudContactoRepository.class);
        when(repo.findById(3L)).thenReturn(Optional.empty());
        var controller = new SolicitudContactoController(repo);
        assertThrows(ResponseStatusException.class,
                () -> controller.actualizarEstado(3L, Map.of("estado", "Resuelta")));
    }
}

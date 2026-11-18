package cl.corpaliv.msvinculacion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_contacto")
public class SolicitudContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 254)
    private String correo;

    @Column(nullable = false, length = 150)
    private String tipoColaboracion;

    @Column(nullable = false, length = 2000)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false, length = 30)
    private String estado;

    public SolicitudContacto() {}

    @PrePersist
    public void antesDeGuardar() {
        if (fechaCreacion == null) fechaCreacion = LocalDateTime.now();
        if (estado == null || estado.isBlank()) estado = "Pendiente";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTipoColaboracion() { return tipoColaboracion; }
    public void setTipoColaboracion(String tipoColaboracion) { this.tipoColaboracion = tipoColaboracion; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

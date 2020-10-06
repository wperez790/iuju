package ar.iua.iuju.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "id del evento")
    @Column(name="id_evento")
    private int id;
    
    private String nombre;
    
    @ApiModelProperty(notes = "Fecha de inicio de turno")
    @NotNull(message = "fecha Inicio no debe ser Nulo")
    private Date fechaInicio;
    
    @ApiModelProperty(notes = "Fecha de finalización de turno")
    private Date fechaFin;
    
    @ApiModelProperty(notes = "Descripcion del motivo del evento")
    private String descripcion;
    
    @ApiModelProperty(notes = "Mail de contacto")
    private Integer urgencia;
    
    @ApiModelProperty(notes = "categoria ")
    private String categoria;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getUrgencia() {
		return urgencia;
	}

	public void setUrgencia(Integer urgencia) {
		this.urgencia = urgencia;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
    
    

	

    
}
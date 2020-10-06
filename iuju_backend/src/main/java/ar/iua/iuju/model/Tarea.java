package ar.iua.iuju.model;

import java.sql.Time;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "id de la tarea")
    @Column(name="id_tarea")
    private int id;

    @ApiModelProperty(notes = "Hora para mandar la notificación", required = false)
    private Time horaRecordatorio;
    
    @ApiModelProperty(notes = "Tareas completadas se marcan en true")
    private Boolean completada;
    
    @ApiModelProperty(notes = "Nombre de la tarea", required = true)
    private String nombre;
    
    @ApiModelProperty(notes = "Descripcion de la tarea", required = true)
    private String descripcion;
    
    private Integer urgencia;
    
    private Integer complejidad;
    
    private String categoria;
    

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Time getHoraRecordatorio() {
		return horaRecordatorio;
	}

	public void setHoraRecordatorio(Time horaRecordatorio) {
		this.horaRecordatorio = horaRecordatorio;
	}

	public Boolean getCompletada() {
		return completada;
	}

	public void setCompletada(Boolean completada) {
		this.completada = completada;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public Integer getComplejidad() {
		return complejidad;
	}

	public void setComplejidad(Integer complejidad) {
		this.complejidad = complejidad;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
    
    
	
    
    

}
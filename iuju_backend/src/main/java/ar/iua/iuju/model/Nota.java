package ar.iua.iuju.model;

import java.sql.Time;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "notas")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "id de la nota")
    @Column(name="id_nota")
    private int id;
    
    @ApiModelProperty(notes = "Fecha de creación de la nota", required = true)
    private Date fechaCreacion;
    
    private Boolean oculta;
    
    private String titulo;
    
    private String detalle;
    
    private String pathToImg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Boolean getOculta() {
		return oculta;
	}

	public void setOculta(Boolean oculta) {
		this.oculta = oculta;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getPathToImg() {
		return pathToImg;
	}

	public void setPathToImg(String pathToImg) {
		this.pathToImg = pathToImg;
	}
    

	
	
    
    

}
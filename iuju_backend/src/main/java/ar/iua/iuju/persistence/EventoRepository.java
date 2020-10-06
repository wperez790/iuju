package ar.iua.iuju.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.iua.iuju.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

		@Query(value= "SELECT * FROM eventos where DATE(fecha_inicio) = DATE(?1)", nativeQuery = true)
		public List<Evento> findByFechaInicio(Date fecha);
		public List<Evento> findByUrgencia(Integer id);
		public List<Evento> findByCategoria(String categoria);
	

}

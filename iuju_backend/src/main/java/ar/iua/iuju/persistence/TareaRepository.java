package ar.iua.iuju.persistence;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.iua.iuju.model.Tarea;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

		public List<Tarea> findByHoraRecordatorio(Time hora);
		public List<Tarea> findByComplejidad(Integer complejidad);
	

}

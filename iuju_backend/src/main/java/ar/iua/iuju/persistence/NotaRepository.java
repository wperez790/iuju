package ar.iua.iuju.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.iua.iuju.model.Nota;

public interface NotaRepository extends JpaRepository<Nota, Integer> {

		// public List<Nota> findAllNotasOrderByFechaCreacion();
		
	

}

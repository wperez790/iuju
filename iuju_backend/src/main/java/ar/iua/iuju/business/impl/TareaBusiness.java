package ar.iua.iuju.business.impl;


import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.iua.iuju.business.ITareaBusiness;
import ar.iua.iuju.business.exception.BusinessException;
import ar.iua.iuju.business.exception.NotFoundException;
import ar.iua.iuju.model.Tarea;
import ar.iua.iuju.persistence.TareaRepository;

@Service
public class TareaBusiness implements ITareaBusiness {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TareaRepository TareaDAO;
	

	public TareaBusiness() {
	}

	@Override
	public List<Tarea> list() throws BusinessException {
		try {
			return TareaDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}

	}

	@Override
	public Tarea save(Tarea Tarea) throws BusinessException, NotFoundException {
		
		try {
			return TareaDAO.save(Tarea);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		/*Optional<Tarea> op = null;
		try {
			op = TareaDAO.findById(Tarea.getId());
			if (!op.isPresent())
				throw new NotFoundException("No se encuentra la Tarea con id=" + Tarea.getId());
			else {
			Tarea Tarea_ = op.get();
			return TareaDAO.save(Tarea_);
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}*/	
		

	}

	@Override
	public void remove(int idTarea) throws BusinessException, NotFoundException {
		Optional<Tarea> op = null;

		try {
			op = TareaDAO.findById(idTarea);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la Tarea con id=" + idTarea);
		try {
			//SET Tarea COMO INACTIVO
			Tarea Tarea = op.get();
			Tarea.setCompletada(false);
			TareaDAO.save(Tarea);
			//TareaDAO.deleteById(idTarea);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public Tarea load(int idTarea) throws BusinessException, NotFoundException {
		Optional<Tarea> op = null;
		try {
			op = TareaDAO.findById(idTarea);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la Tarea con id=" + idTarea);
		return op.get();

	}

/*
	@Override
	public List<Tarea> findByFechaInicio(Date fecha) throws BusinessException, NotFoundException {
		try {
			return TareaDAO.findByFechaInicio(fecha);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}
*/
	@Override
	public List<Tarea> findByHoraRecordatorio(Time hora) throws BusinessException, NotFoundException {
		try {
			return TareaDAO.findByHoraRecordatorio(hora);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Tarea> findByComplejidad(Integer complejidad) throws BusinessException, NotFoundException {
		try {
			return TareaDAO.findByComplejidad(complejidad);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

}

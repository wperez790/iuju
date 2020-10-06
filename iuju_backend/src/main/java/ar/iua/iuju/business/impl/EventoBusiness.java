package ar.iua.iuju.business.impl;


import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.iua.iuju.business.IEventoBusiness;
import ar.iua.iuju.business.exception.BusinessException;
import ar.iua.iuju.business.exception.NotFoundException;
import ar.iua.iuju.model.Evento;
import ar.iua.iuju.model.Tarea;
import ar.iua.iuju.persistence.EventoRepository;

@Service
public class EventoBusiness implements IEventoBusiness {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EventoRepository EventoDAO;
	

	public EventoBusiness() {
	}

	@Override
	public List<Evento> list() throws BusinessException {
		try {
			return EventoDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}

	}

	@Override
	public Evento save(Evento Evento) throws BusinessException, NotFoundException {
		
		try {
			return EventoDAO.save(Evento);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		/*Optional<Evento> op = null;
		try {
			op = EventoDAO.findById(Evento.getId());
			if (!op.isPresent())
				throw new NotFoundException("No se encuentra la Evento con id=" + Evento.getId());
			else {
			Evento Evento_ = op.get();
			return EventoDAO.save(Evento_);
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}*/	
		

	}

	@Override
	public void remove(int idEvento) throws BusinessException, NotFoundException {
		Optional<Evento> op = null;

		try {
			op = EventoDAO.findById(idEvento);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la Evento con id=" + idEvento);
		try {
			//SET Evento COMO INACTIVO
			Evento Evento = op.get();
			EventoDAO.save(Evento);
			//EventoDAO.deleteById(idEvento);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public Evento load(int idEvento) throws BusinessException, NotFoundException {
		Optional<Evento> op = null;
		try {
			op = EventoDAO.findById(idEvento);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la Evento con id=" + idEvento);
		return op.get();

	}


	@Override
	public List<Evento> findByFechaInicio(Date fecha) throws BusinessException, NotFoundException {
		try {
			return EventoDAO.findByFechaInicio(fecha);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Evento> findByUrgencia(Integer urgencia) throws BusinessException, NotFoundException {
		try {
			return EventoDAO.findByUrgencia(urgencia);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Evento> findByCategoria(String categoria) throws BusinessException, NotFoundException {
		try {
			return EventoDAO.findByCategoria(categoria);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

}

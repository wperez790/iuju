package ar.iua.iuju.business.impl;


import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.iua.iuju.business.INotaBusiness;
import ar.iua.iuju.business.exception.BusinessException;
import ar.iua.iuju.business.exception.NotFoundException;
import ar.iua.iuju.model.Nota;
import ar.iua.iuju.persistence.NotaRepository;

@Service
public class NotaBusiness implements INotaBusiness {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private NotaRepository NotaDAO;
	

	public NotaBusiness() {
	}

	@Override
	public List<Nota> list() throws BusinessException {
		try {
			return NotaDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}

	}

	@Override
	public Nota save(Nota Nota) throws BusinessException, NotFoundException {
		
		try {
			return NotaDAO.save(Nota);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		/*Optional<Nota> op = null;
		try {
			op = NotaDAO.findById(Nota.getId());
			if (!op.isPresent())
				throw new NotFoundException("No se encuentra la Nota con id=" + Nota.getId());
			else {
			Nota Nota_ = op.get();
			return NotaDAO.save(Nota_);
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}*/	
		

	}

	@Override
	public void remove(int idNota) throws BusinessException, NotFoundException {
		Optional<Nota> op = null;

		try {
			op = NotaDAO.findById(idNota);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la Nota con id=" + idNota);
		try {
			//SET Nota COMO INACTIVO
			Nota Nota = op.get();
			NotaDAO.save(Nota);
			//NotaDAO.deleteById(idNota);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public Nota load(int idNota) throws BusinessException, NotFoundException {
		Optional<Nota> op = null;
		try {
			op = NotaDAO.findById(idNota);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (!op.isPresent())
			throw new NotFoundException("No se encuentra la Nota con id=" + idNota);
		return op.get();

	}
/*
	@Override
	public List<Nota> findAllNotasOrderByFechaCreacionDesc() throws BusinessException, NotFoundException {
		List<Nota> op = null;
		try {
			op = NotaDAO.findAllNotasOrderByFechaCreacionDesc();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (op == null)
			throw new NotFoundException("No se encuentran notas");
		return op;
	}
*/

}

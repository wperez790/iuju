package ar.iua.iuju.business;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import ar.iua.iuju.business.exception.BusinessException;
import ar.iua.iuju.business.exception.NotFoundException;
import ar.iua.iuju.model.Evento;
import ar.iua.iuju.model.Evento;



public interface IEventoBusiness {

	public List<Evento> list() throws BusinessException;

	public Evento load(int idEvento) throws BusinessException, NotFoundException;

	public Evento save(Evento Evento) throws BusinessException, NotFoundException;

	public void remove(int idEvento) throws BusinessException, NotFoundException;
	
	public List<Evento> findByFechaInicio(Date fecha) throws BusinessException, NotFoundException;
	
	public List<Evento> findByUrgencia(Integer id) throws BusinessException, NotFoundException;
	
	public List<Evento> findByCategoria(String categoria) throws BusinessException, NotFoundException;
}

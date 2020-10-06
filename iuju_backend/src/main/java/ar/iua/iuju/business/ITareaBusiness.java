package ar.iua.iuju.business;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import ar.iua.iuju.business.exception.BusinessException;
import ar.iua.iuju.business.exception.NotFoundException;
import ar.iua.iuju.model.Tarea;



public interface ITareaBusiness {

	public List<Tarea> list() throws BusinessException;

	public Tarea load(int idTarea) throws BusinessException, NotFoundException;

	public Tarea save(Tarea Tarea) throws BusinessException, NotFoundException;

	public void remove(int idTarea) throws BusinessException, NotFoundException;
	
	// public List<Tarea> findByFechaInicio(Date fecha) throws BusinessException, NotFoundException;
	
	public List<Tarea> findByHoraRecordatorio(Time hora) throws BusinessException, NotFoundException;
	
	public List<Tarea> findByComplejidad(Integer complejidad) throws BusinessException, NotFoundException;

}

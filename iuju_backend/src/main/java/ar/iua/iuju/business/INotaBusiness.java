package ar.iua.iuju.business;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import ar.iua.iuju.business.exception.BusinessException;
import ar.iua.iuju.business.exception.NotFoundException;
import ar.iua.iuju.model.Nota;



public interface INotaBusiness {

	public List<Nota> list() throws BusinessException;

	public Nota load(int idNota) throws BusinessException, NotFoundException;

	public Nota save(Nota Nota) throws BusinessException, NotFoundException;

	public void remove(int idNota) throws BusinessException, NotFoundException;
	
	//public List<Nota> findAllNotasOrderByFechaCreacionDesc() throws BusinessException, NotFoundException;
	

}

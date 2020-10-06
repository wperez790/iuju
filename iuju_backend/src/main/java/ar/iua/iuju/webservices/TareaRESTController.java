package ar.iua.iuju.webservices;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.iua.iuju.business.ITareaBusiness;
import ar.iua.iuju.business.exception.BusinessException;
import ar.iua.iuju.business.exception.NotFoundException;
import ar.iua.iuju.model.Tarea;

@RestController
@CrossOrigin
@RequestMapping(RESTConstants.URL_TAREAS)
public class TareaRESTController {

	@Autowired
	private ITareaBusiness TareaBusiness;



	@GetMapping("")
	public ResponseEntity<List<Tarea>> list() {
		
		try {
			return new ResponseEntity<List<Tarea>>(TareaBusiness.list(), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<List<Tarea>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Tarea> load(@PathVariable("id") int idTarea) {
		
		try {
			return new ResponseEntity<Tarea>(TareaBusiness.load(idTarea), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Tarea>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Tarea>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/search", params = "hora")
	public ResponseEntity<List<Tarea>> findByHoraRecordatorio( @DateTimeFormat(pattern="HH:mm") Time hora) {
		try {
			List<Tarea> Tareas = TareaBusiness.findByHoraRecordatorio(hora);
			return new ResponseEntity<List<Tarea>>(Tareas,HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Tarea>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<List<Tarea>>(responseHeaders,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "")
	public ResponseEntity<String> insert(@RequestBody Tarea Tarea) {
		
		try {
			//	Save Transaction in db
			TareaBusiness.save(Tarea);
			//	Return new created id
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("idTarea", RESTConstants.URL_TAREAS + "/" + Tarea.getId());
			return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
			
		} catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

	@PutMapping(value = "")
	public ResponseEntity<String> update(@RequestBody Tarea Tarea) {
		
		try {
			TareaBusiness.save(Tarea);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int idTarea) {
		
		try {
			TareaBusiness.remove(idTarea);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	
}

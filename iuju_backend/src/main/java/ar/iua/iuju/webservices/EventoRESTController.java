package ar.iua.iuju.webservices;

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

import ar.iua.iuju.business.IEventoBusiness;
import ar.iua.iuju.business.exception.BusinessException;
import ar.iua.iuju.business.exception.NotFoundException;
import ar.iua.iuju.model.Evento;

@RestController
@CrossOrigin
@RequestMapping(RESTConstants.URL_EVENTOS)
public class EventoRESTController {

	@Autowired
	private IEventoBusiness EventoBusiness;



	@GetMapping("")
	public ResponseEntity<List<Evento>> list() {
		
		try {
			return new ResponseEntity<List<Evento>>(EventoBusiness.list(), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<List<Evento>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Evento> load(@PathVariable("id") int idEvento) {
		
		try {
			return new ResponseEntity<Evento>(EventoBusiness.load(idEvento), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Evento>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Evento>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/search", params = "fecha")
	public ResponseEntity<List<Evento>> findByFechaInicio(@RequestParam(name = "fecha") @DateTimeFormat(pattern="yyyy-MM-dd") Date fecha) {
		try {
			List<Evento> Eventos = EventoBusiness.findByFechaInicio(fecha);
			return new ResponseEntity<List<Evento>>(Eventos,HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Evento>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<List<Evento>>(responseHeaders,HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "")
	public ResponseEntity<String> insert(@RequestBody Evento Evento) {
		
		try {
			//	Save Transaction in db
			EventoBusiness.save(Evento);
			//	Return new created id
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("idEvento", RESTConstants.URL_EVENTOS + "/" + Evento.getId());
			return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
			
		} catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

	@PutMapping(value = "")
	public ResponseEntity<String> update(@RequestBody Evento Evento) {
		
		try {
			EventoBusiness.save(Evento);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int idEvento) {
		
		try {
			EventoBusiness.remove(idEvento);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	
}

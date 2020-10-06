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

import ar.iua.iuju.business.INotaBusiness;
import ar.iua.iuju.business.exception.BusinessException;
import ar.iua.iuju.business.exception.NotFoundException;
import ar.iua.iuju.model.Nota;

@RestController
@CrossOrigin
@RequestMapping(RESTConstants.URL_NOTAS)
public class NotaRESTController {

	@Autowired
	private INotaBusiness NotaBusiness;



	@GetMapping("")
	public ResponseEntity<List<Nota>> list() {
		
		try {
			return new ResponseEntity<List<Nota>>(NotaBusiness.list(), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<List<Nota>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Nota> load(@PathVariable("id") int idNota) {
		
		try {
			return new ResponseEntity<Nota>(NotaBusiness.load(idNota), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Nota>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Nota>(HttpStatus.NOT_FOUND);
		}
	}
	/*
	@GetMapping(value = "/ordered")
	public ResponseEntity<List<Nota>> findAllNotasOrderByFechaCreacionDesc() {
		try {
			List<Nota> Notas = NotaBusiness.findAllNotasOrderByFechaCreacionDesc();
			return new ResponseEntity<List<Nota>>(Notas,HttpStatus.OK);
		}catch (BusinessException e) {
			return new ResponseEntity<List<Nota>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (NotFoundException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("error", e.getMessage());
			return new ResponseEntity<List<Nota>>(responseHeaders,HttpStatus.NOT_FOUND);
		}
	}*/

	@PostMapping(value = "")
	public ResponseEntity<String> insert(@RequestBody Nota Nota) {
		
		try {
			//	Save Transaction in db
			NotaBusiness.save(Nota);
			//	Return new created id
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("idNota", RESTConstants.URL_NOTAS + "/" + Nota.getId());
			return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
			
		} catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

	@PutMapping(value = "")
	public ResponseEntity<String> update(@RequestBody Nota Nota) {
		
		try {
			NotaBusiness.save(Nota);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int idNota) {
		
		try {
			NotaBusiness.remove(idNota);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	
}

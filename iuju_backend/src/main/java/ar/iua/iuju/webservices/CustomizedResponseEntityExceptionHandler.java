package ar.iua.iuju.webservices;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import ar.iua.iuju.business.exception.BusinessException;
import ar.iua.iuju.business.exception.NotFoundException;
import ar.iua.iuju.model.ErrorDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@CrossOrigin
@RestController
public class CustomizedResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /* General Exceptions */
    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity handleAllExceptions(Exception ex, HttpServletRequest request) {
        log.warn(ex.toString());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        log.warn(ex.toString());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn(ex.toString());
        //  Obtener todos los campos con error
        //  Y generar un mensaje acorde
        int errCount = ex.getBindingResult().getFieldErrorCount();
        String message = "There were errors with the expected fields of the object:";
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        for (int i = 0; i < errCount; i++) {
            message += "[field:" + errors.get(i).getField() + ", ";
            message += "error:" + errors.get(i).getDefaultMessage() + "],";
        }
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message,
                request.getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
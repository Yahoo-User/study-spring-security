package org.zerock.myapp.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

/**
 * *Important :
 * 		The global exception handler with @ControllerAdvice (or @RestControllerAdvice) + @ExceptionHandler precedes
 * 		the exception handler with mapped to the"/error" (@Controller + `ErrorController` Interface).
 */

//@ControllerAdvice				// OK, (1) method
@RestControllerAdvice			// OK, (2) method, @ControllerAdvice + @ResponseBody
public final class GlobalExceptionHandler {
	
	/**
	 *  (1) method
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleAllExceptions(Exception e, WebRequest req) {
		log.trace("handleThrowable({}, {}) invoked.", e, req);
		
		ErrorDetails errorDetails = 
				new ErrorDetails(
						HttpStatus.INTERNAL_SERVER_ERROR.value(), 
						e.getMessage(), 
						req.getDescription(true)
				);
		
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	} // handleThrowable
	 */
	
	 // (2) method, @RestControllerAdvice = @ControllerAdvice + @ResponseBody
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public @ResponseBody ErrorDetails handleThrowable(Throwable t, WebRequest webReq, HttpServletRequest req) {
		log.trace("handleThrowable({}, {}, {}) invoked.", t, webReq,  req);
		
		// --- 1 -----------------
		t.printStackTrace();

		// --- 2 -----------------
		ErrorDetails details = new ErrorDetails();
		
		details.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		details.setDescription(webReq.getDescription(true));
		
		details.setHttpMethod(req.getMethod());
		details.setRequestURI(req.getRequestURI());
		details.setQueryString(req.getQueryString());
		details.setRequestParam(req.getParameterMap());
		
		details.setExceptionType(t.getClass().getName());
		details.setExceptionMessage(t.getMessage());
		
		return details;
	} // handleThrowable

} // end class


@Data
final class ErrorDetails {
	private HttpStatus statusCode;
	private String description;
	
	private String httpMethod;
	private String requestURI;
	private String queryString;
	private Map<String, String[]> requestParam;
	
	private String exceptionType;
	private String exceptionMessage;
	private String detailMessage;
		
	
} // end class


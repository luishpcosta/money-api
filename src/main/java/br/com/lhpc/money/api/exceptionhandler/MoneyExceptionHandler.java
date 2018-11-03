package br.com.lhpc.money.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MoneyExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private  MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String message = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String error = ex.getCause().toString();
		List<Erro> erros = Arrays.asList(new Erro(message, error));		
		return super.handleExceptionInternal(ex, erros, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> erros = geraListaDeErros(ex.getBindingResult());
		return super.handleExceptionInternal(ex, erros,headers, status, request);
	}

	private List<Erro> geraListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();
		
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			erros.add(new Erro(messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()), fieldError.toString()));
		}
		
		return erros;
		
	}
	
	public static class Erro {

		
		private String message;
		private String error;
		
		public Erro( String message, String error) {
			this.message = message;
			this.error = error;
		}
		
		public String getMessage() {
			return message;
		}
		public String getError() {
			return error;
		}

	}
	
	

}

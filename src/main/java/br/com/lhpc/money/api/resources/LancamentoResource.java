package br.com.lhpc.money.api.resources;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lhpc.money.api.event.ResourceCreatedEvent;
import br.com.lhpc.money.api.exceptionhandler.MoneyExceptionHandler.Erro;
import br.com.lhpc.money.api.models.Lancamento;
import br.com.lhpc.money.api.repository.LancamentoRepository;
import br.com.lhpc.money.api.repository.filter.LancamentoFilter;
import br.com.lhpc.money.api.service.LancamentoService;
import br.com.lhpc.money.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private  MessageSource messageSource;

	
	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> buscarPorId(@PathVariable long id){
		Lancamento lancamento = lancamentoRepository.findById(id);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		lancamento = lancamentoService.salvar(lancamento);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, lancamento.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		lancamentoRepository.deleteById(id);
	}
	
	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException( PessoaInexistenteOuInativaException ex){
		String message = messageSource.getMessage("mensagem.pessoa.nao.existe", null, LocaleContextHolder.getLocale());
		String error = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(message, error));	
		return ResponseEntity.badRequest().body(erros);
		
	}
}

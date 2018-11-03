package br.com.lhpc.money.api.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lhpc.money.api.event.ResourceCreatedEvent;
import br.com.lhpc.money.api.models.Pessoa;
import br.com.lhpc.money.api.repository.PessoaRepository;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		pessoa = pessoaRepository.save(pessoa);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, pessoa.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscarPorID(@PathVariable long id) {
		Pessoa pessoa = pessoaRepository.findById(id);
		return pessoa != null ? ResponseEntity.ok().body(pessoa) : ResponseEntity.notFound().build();
	}

}

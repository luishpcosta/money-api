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
import br.com.lhpc.money.api.models.Categoria;
import br.com.lhpc.money.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		categoria  = categoriaRepository.save(categoria); 
		publisher.publishEvent(new ResourceCreatedEvent(this, response, categoria.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscarPorID(@PathVariable long id) {
		Categoria categoria = categoriaRepository.findById(id);
		return  categoria != null ? ResponseEntity.ok().body(categoria) : ResponseEntity.notFound().build() ;
		
	}

}

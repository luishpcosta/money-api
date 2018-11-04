package br.com.lhpc.money.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.lhpc.money.api.models.Categoria;
import br.com.lhpc.money.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria atualizar(Long id, Categoria categoria) {
		
		Categoria categoriaSalva = categoriaRepository.findById(id.longValue());
		
		if(categoriaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(categoria, categoriaSalva, "id");
		return categoriaRepository.save(categoriaSalva);
	}
	
	

}

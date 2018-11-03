package br.com.lhpc.money.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.lhpc.money.api.models.Pessoa;
import br.com.lhpc.money.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaById(id);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return pessoaRepository.save(pessoa);
	}


	public void atualizarAtivo(Long id, Boolean ativo) {
		Pessoa pessoa = pessoaById(id);
		pessoa.setAtivo(ativo);
		pessoaRepository.save(pessoa);
	}

	private Pessoa pessoaById(Long id) {
		Pessoa pessoaSalva = pessoaRepository.getOne(id);
		
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
}

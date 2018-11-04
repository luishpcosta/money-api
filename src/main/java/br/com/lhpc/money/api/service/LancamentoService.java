package br.com.lhpc.money.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lhpc.money.api.models.Lancamento;
import br.com.lhpc.money.api.models.Pessoa;
import br.com.lhpc.money.api.repository.LancamentoRepository;
import br.com.lhpc.money.api.repository.PessoaRepository;
import br.com.lhpc.money.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento salvar( Lancamento lancamento) {
		
		Pessoa pessoa = pessoaRepository.findById(lancamento.getPessoa().getId().longValue());
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}
	
	

}

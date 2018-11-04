package br.com.lhpc.money.api.repository.lancamento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.lhpc.money.api.models.Lancamento;
import br.com.lhpc.money.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

}

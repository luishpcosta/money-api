package br.com.lhpc.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lhpc.money.api.models.Lancamento;
import br.com.lhpc.money.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> , LancamentoRepositoryQuery{
	
	Lancamento findById(long id);
	

}

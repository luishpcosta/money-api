package br.com.lhpc.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lhpc.money.api.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	Pessoa findById(long id);
}

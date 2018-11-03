package br.com.lhpc.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lhpc.money.api.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
}

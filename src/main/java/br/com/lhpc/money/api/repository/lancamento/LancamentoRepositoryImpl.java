package br.com.lhpc.money.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.lhpc.money.api.models.Lancamento;
import br.com.lhpc.money.api.models.Lancamento_;
import br.com.lhpc.money.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] restrictions = criarRestricoes(lancamentoFilter, builder, root);
		
		criteria.where(restrictions);
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		
		adicionaRestricaoDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(),pageable, total(lancamentoFilter));
	}


	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {

		List<Predicate> restricoes = new ArrayList<>();
		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			restricoes.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),
					"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if(lancamentoFilter.getDataVencimentoDe() != null) {
			restricoes.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if(lancamentoFilter.getDataVencimentoAte() != null) {
			restricoes.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
		}

		return restricoes.toArray(new Predicate[restricoes.size()]);
	}
	
	private void adicionaRestricaoDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
		
	}
	

	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] restricoes = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(restricoes);
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}

}

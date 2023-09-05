package ProdutoSpecifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import Seletor.ProdutoSeletor;
import br.sc.senac.dw.model.entidade.Produto;
import jakarta.persistence.criteria.Predicate;

public class ProdutoSpecification {

	public static Specification<Produto> comFiltros(ProdutoSeletor seletor) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (seletor.getNome() != null) {
				// WHERE/AND COLUNA OPERADOR VALOR
				// WHERE nome like %Café%
				predicates.add(cb.like(cb.lower(root.get("nome")), "%" + seletor.getNome().toLowerCase() + "%"));
			}

			// TODO como filtrar por "FABRICANTES.NOME"?
			// https://stackoverflow.com/questions/6396877/openjpa-criteriabuilder-nested-object-property-fetch
			if (seletor.getFabricante() != null) {
				// WHERE p.fabricante like '%Rider%'
				// WHERE f.nome like '%Rider%'
				// JPQL = Java Persistence Query Language
				predicates.add(
						cb.like(root.join("fabricanteDoProduto").get("nome"), "%" + seletor.getFabricante() + "%"));
			}

			if (seletor.getPesoMinimo() != null && seletor.getPesoMaximo() != null) {
				// WHERE peso BETWEEN min AND max
				predicates.add(cb.between(root.get("peso"), seletor.getPesoMinimo(), seletor.getPesoMaximo()));
			} else if (seletor.getPesoMinimo() != null) {
				// WHERE peso >= min
				predicates.add(cb.greaterThanOrEqualTo(root.get("peso"), seletor.getPesoMinimo()));
			} else if (seletor.getPesoMaximo() != null) {
				// WHERE peso <= max
				predicates.add(cb.lessThanOrEqualTo(root.get("peso"), seletor.getPesoMaximo()));
			}

			if (seletor.getValorMinimo() != null && seletor.getValorMaximo() != null) {
				predicates.add(cb.between(root.get("valor"), seletor.getValorMinimo(), seletor.getValorMaximo()));
			}

			if (seletor.getFabricante() != null && !seletor.getFabricante().isEmpty()) {
				predicates.add(cb.like(root.join("fabricanteDoProduto").get("CNPJ"), "%" + seletor.getFabricante() + "%"));
			}

			// TODO Adicionar outros filtros aqui
//		            private Double valorMinimo;
//		            private Double valorMaximo;
//		            private LocalDate dataCadastroInicial;
//		            private LocalDate dataCadastroFinal;
			// Por CNPJ

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}

//	    private ProdutoSeletor seletor;
//
//	    public ProdutoEspecification(ProdutoSeletor seletor) {
//	        this.seletor = seletor;
//	    }
//
//	    @Override
//	   
//	     public Predicate toPredicate(Root<Produto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//	        Predicate predicate = criteriaBuilder.conjunction();
//
//	        if (seletor.getPesoMinimo() != null && seletor.getPesoMaximo() != null) {
//	            predicate = criteriaBuilder.and(predicate,
//	                criteriaBuilder.between(root.get("peso"), seletor.getPesoMinimo(), seletor.getPesoMaximo()));
//	        }
//
//	        if (seletor.getValorMinimo() != null && seletor.getValorMaximo() != null) {
//	            predicate = criteriaBuilder.and(predicate,
//	                criteriaBuilder.between(root.get("valor"), seletor.getValorMinimo(), seletor.getValorMaximo()));
//	        }
//
//	        if (seletor.getFabricante() != null && !seletor.getFabricante().isEmpty()) {
//	            predicate = criteriaBuilder.and(predicate,
//	                criteriaBuilder.equal(root.get("fabricante").get("cnpj"), seletor.getFabricante()));
//	        }
//
//	        return predicate;
//	    }

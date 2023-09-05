package br.sc.senac.dw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ProdutoSpecifications.ProdutoSpecification;
import Seletor.ProdutoSeletor;
import br.sc.senac.dw.model.entidade.Produto;
import br.sc.senac.dw.model.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> buscarPorSeletor(ProdutoSeletor seletor) {
    	Specification<Produto> specification = ProdutoSpecification.comFiltros(seletor);
		return produtoRepository.findAll(specification);
    }

    public Produto inserir(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }

}

package br.com.coffeegestao.service;

import br.com.coffeegestao.model.Produto;
import br.com.coffeegestao.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrar(Produto produto) {
        validar(produto);
        return produtoRepository.salvar(produto);
    }
}
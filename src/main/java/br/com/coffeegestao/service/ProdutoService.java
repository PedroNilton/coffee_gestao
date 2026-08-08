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

    public void atualizar(Produto produto) {
        validar(produto);
        produtoRepository.atualizar(produto);
    }

    public void remover(int id) {
        produtoRepository.deletar(id);
    }

    public void baixarEstoque(int id, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        produtoRepository.baixarEstoque(id, quantidade);
    }
}
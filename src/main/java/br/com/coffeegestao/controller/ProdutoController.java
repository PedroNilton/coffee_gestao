package br.com.coffeegestao.controller;

import br.com.coffeegestao.model.Produto;
import br.com.coffeegestao.repository.ProdutoRepository;
import br.com.coffeegestao.service.ProdutoService;

import java.util.List;
import java.util.Optional;

public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController() {
        this.produtoService = new ProdutoService(new ProdutoRepository());
    }

    public Produto cadastrar(String nome, String descricao, double preco, int quantidadeEstoque) {
        Produto produto = new Produto(nome, descricao, preco, quantidadeEstoque);
        return produtoService.cadastrar(produto);
    }

    public void atualizar(Produto produto) {
        produtoService.atualizar(produto);
    }

    public void remover(int id) {
        produtoService.remover(id);
    }

    public Optional<Produto> buscarPorId(int id) {
        return produtoService.buscarPorId(id);
    }

    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }
}
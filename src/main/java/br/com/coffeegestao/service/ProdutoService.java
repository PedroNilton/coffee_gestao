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

    public Optional<Produto> buscarPorId(int id) {
        return produtoRepository.buscarPorId(id);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.listarTodos();
    }

    private void validar(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        }
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        if (produto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa.");
        }
    }
}
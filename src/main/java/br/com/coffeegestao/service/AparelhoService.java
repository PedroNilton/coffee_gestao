package br.com.coffeegestao.service;

import br.com.coffeegestao.model.Aparelho;
import br.com.coffeegestao.repository.AparelhoRepository;

import java.util.List;
import java.util.Optional;

public class AparelhoService {

    private final AparelhoRepository aparelhoRepository;

    public AparelhoService(AparelhoRepository aparelhoRepository) {
        this.aparelhoRepository = aparelhoRepository;
    }

    public Aparelho cadastrar(Aparelho aparelho) {
        validar(aparelho);
        return aparelhoRepository.salvar(aparelho);
    }

    public void atualizar(Aparelho aparelho) {
        validar(aparelho);
        aparelhoRepository.atualizar(aparelho);
    }

    public void remover(int id) {
        aparelhoRepository.deletar(id);
    }

    public Optional<Aparelho> buscarPorId(int id) {
        return aparelhoRepository.buscarPorId(id);
    }

    public List<Aparelho> listarPorClienteId(int clienteId) {
        return aparelhoRepository.listarPorClienteId(clienteId);
    }

    public List<Aparelho> listarTodos() {
        return aparelhoRepository.listarTodos();
    }

    private void validar(Aparelho aparelho) {
        if (aparelho.getClienteId() <= 0) {
            throw new IllegalArgumentException("Aparelho precisa estar vinculado a um cliente válido.");
        }

        if (aparelho.getTipo() == null || aparelho.getTipo().isBlank()) {
            throw new IllegalArgumentException("Tipo do aparelho é obrigatório.");
        }
    }
}
package br.com.coffeegestao.controller;

import br.com.coffeegestao.model.Aparelho;
import br.com.coffeegestao.repository.AparelhoRepository;
import br.com.coffeegestao.service.AparelhoService;

import java.util.List;
import java.util.Optional;

public class AparelhoController {

    private final AparelhoService aparelhoService;

    public AparelhoController() {
        this.aparelhoService = new AparelhoService(new AparelhoRepository());
    }

    public Aparelho cadastrar(int clienteId, String tipo, String marca, String modelo, String numeroSerie, String observacoes) {
        Aparelho aparelho = new Aparelho(clienteId, tipo, marca, modelo, numeroSerie, observacoes);
        return aparelhoService.cadastrar(aparelho);
    }

    public void atualizar(Aparelho aparelho) {
        aparelhoService.atualizar(aparelho);
    }

    public void remover(int id) {
        aparelhoService.remover(id);
    }

    public Optional<Aparelho> buscarPorId(int id) {
        return aparelhoService.buscarPorId(id);
    }

    public List<Aparelho> listarPorClienteId(int clienteId) {
        return aparelhoService.listarPorClienteId(clienteId);
    }

    public List<Aparelho> listarTodos() {
        return aparelhoService.listarTodos();
    }
}
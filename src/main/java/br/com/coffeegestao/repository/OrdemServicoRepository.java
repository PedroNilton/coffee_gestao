package br.com.coffeegestao.service;

import br.com.coffeegestao.model.OrdemServico;
import br.com.coffeegestao.model.StatusOrdemServico;
import br.com.coffeegestao.repository.OrdemServicoRepository;

import java.time.LocalDateTime;

public class OrdemServicoService {

    private final OrdemServicoRepository repository;

    public OrdemServicoService(OrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServico abrir(int clienteId, int aparelhoId, String defeitoRelatado) {
        if (defeitoRelatado == null || defeitoRelatado.isBlank()) {
            throw new IllegalArgumentException("Descreva o defeito relatado pelo cliente.");
        }
        if (clienteId <= 0 || aparelhoId <= 0) {
            throw new IllegalArgumentException("Cliente e aparelho precisam ser selecionados.");
        }

        OrdemServico ordem = new OrdemServico(clienteId, aparelhoId, defeitoRelatado);
        return repository.salvar(ordem);
    }
}
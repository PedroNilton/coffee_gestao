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

    public void concluir(int id, String solucao, double valorServico) {
        OrdemServico ordem = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada."));

        if (ordem.getStatus() == StatusOrdemServico.CONCLUIDA || ordem.getStatus() == StatusOrdemServico.CANCELADA) {
            throw new IllegalStateException("Essa ordem já foi finalizada e não pode ser alterada.");
        }
        if (ordem.getDiagnostico() == null || ordem.getDiagnostico().isBlank()) {
            throw new IllegalStateException("Registre o diagnóstico antes de concluir a ordem.");
        }

        ordem.setSolucao(solucao);
        ordem.setValorServico(valorServico);
        ordem.setStatus(StatusOrdemServico.CONCLUIDA);
        ordem.setDataFechamento(LocalDateTime.now());

        repository.atualizar(ordem);
    }

    public void cancelar(int id) {
        OrdemServico ordem = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada."));

        if (ordem.getStatus() == StatusOrdemServico.CONCLUIDA) {
            throw new IllegalStateException("Não é possível cancelar uma ordem já concluída.");
        }

        ordem.setStatus(StatusOrdemServico.CANCELADA);
        ordem.setDataFechamento(LocalDateTime.now());
        repository.atualizar(ordem);
    }
    public void registrarDiagnostico(int id, String diagnostico) {
        OrdemServico ordem = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada."));

        ordem.setDiagnostico(diagnostico);
        ordem.setStatus(StatusOrdemServico.EM_ANDAMENTO);
        repository.atualizar(ordem);
    }

    public java.util.List<OrdemServico> listarTodas() {
        return repository.listarTodas();
    }

    public java.util.List<OrdemServico> listarPorClienteId(int clienteId) {
        return repository.listarPorClienteId(clienteId);
    }

    public java.util.Optional<OrdemServico> buscarPorId(int id) {
        return repository.buscarPorId(id);
    }
}

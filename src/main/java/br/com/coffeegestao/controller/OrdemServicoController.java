package br.com.coffeegestao.controller;

import br.com.coffeegestao.model.OrdemServico;
import br.com.coffeegestao.repository.OrdemServicoRepository;
import br.com.coffeegestao.service.OrdemServicoService;

public class OrdemServicoController {

    private final OrdemServicoService service;

    public OrdemServicoController() {
        this.service = new OrdemServicoService(new OrdemServicoRepository());
    }

    public OrdemServico abrir(int clienteId, int aparelhoId, String defeitoRelatado) {
        return service.abrir(clienteId, aparelhoId, defeitoRelatado);
    }

    public void registrarDiagnostico(int id, String diagnostico) {
        service.registrarDiagnostico(id, diagnostico);
    }
    public void concluir(int id, String solucao, double valorServico) {
        service.concluir(id, solucao, valorServico);
    }

    public void cancelar(int id) {
        service.cancelar(id);
    }

    public java.util.List<OrdemServico> listarTodas() {
        return service.listarTodas();
    }

    public java.util.List<OrdemServico> listarPorClienteId(int clienteId) {
        return service.listarPorClienteId(clienteId);
    }

    public java.util.Optional<OrdemServico> buscarPorId(int id) {
        return service.buscarPorId(id);
    }
}
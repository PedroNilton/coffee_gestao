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
}
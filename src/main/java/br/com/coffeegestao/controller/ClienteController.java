package br.com.coffeegestao.controller;

import br.com.coffeegestao.model.Cliente;
import br.com.coffeegestao.repository.ClienteRepository;
import br.com.coffeegestao.service.ClienteService;

import java.util.List;
import java.util.Optional;

public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController() {
        this.clienteService = new ClienteService(new ClienteRepository());
    }

    public Cliente cadastrar(String nome, String telefone, String cpf, String email, String endereco) {
        Cliente cliente = new Cliente(nome, telefone, cpf, email, endereco);
        return clienteService.cadastrar(cliente);
    }

    public void atualizar(Cliente cliente) {
        clienteService.atualizar(cliente);
    }

    public void remover(int id) {
        clienteService.remover(id);
    }

    public Optional<Cliente> buscarPorId(int id) {
        return clienteService.buscarPorId(id);
    }

    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }
}
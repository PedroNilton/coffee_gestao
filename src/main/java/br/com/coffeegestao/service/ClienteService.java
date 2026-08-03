package br.com.coffeegestao.service;

import br.com.coffeegestao.model.Cliente;
import br.com.coffeegestao.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrar(Cliente cliente) {
        validar(cliente);
        return clienteRepository.salvar(cliente);
    }

    public void atualizar(Cliente cliente) {
        validar(cliente);
        clienteRepository.atualizar(cliente);
    }

    public void remover(int id) {
        clienteRepository.deletar(id);
    }

    public Optional<Cliente> buscarPorId(int id) {
        return clienteRepository.buscarPorId(id);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.listarTodos();
    }

    private void validar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório.");
        }

        if (cliente.getCpf() != null && !cliente.getCpf().isBlank() && !cpfValido(cliente.getCpf())) {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }

    private boolean cpfValido(String cpf) {
        String digits = cpf.replaceAll("\\D", "");

        if (digits.length() != 11 || digits.chars().distinct().count() == 1) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (digits.charAt(i) - '0') * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) primeiroDigito = 0;

        if (primeiroDigito != digits.charAt(9) - '0') {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (digits.charAt(i) - '0') * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) segundoDigito = 0;

        return segundoDigito == digits.charAt(10) - '0';
    }
}
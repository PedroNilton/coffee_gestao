package br.com.coffeegestao.service;

import br.com.coffeegestao.model.Usuario;
import br.com.coffeegestao.repository.UsuarioRepository;

import java.util.Optional;

public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos."));

        if (!usuario.isAtivo()) {
            throw new IllegalStateException("Usuário inativo. Contate o administrador.");
        }

        if (!usuario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Email ou senha inválidos.");
        }

        return usuario;
    }
}
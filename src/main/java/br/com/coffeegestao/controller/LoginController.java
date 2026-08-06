package br.com.coffeegestao.controller;

import br.com.coffeegestao.model.Usuario;
import br.com.coffeegestao.repository.UsuarioRepository;
import br.com.coffeegestao.service.AuthService;

public class LoginController {

    private final AuthService authService;

    public LoginController() {
        this.authService = new AuthService(new UsuarioRepository());
    }

    public Usuario login(String email, String senha) {
        return authService.autenticar(email, senha);
    }
}
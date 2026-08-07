package br.com.coffeegestao;

import br.com.coffeegestao.database.DatabaseInitializer;
import br.com.coffeegestao.model.Usuario;
import br.com.coffeegestao.repository.UsuarioRepository;
import br.com.coffeegestao.view.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        criarAdminPadraoSeNecessario();

        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }

    private static void criarAdminPadraoSeNecessario() {
        UsuarioRepository usuarioRepository = new UsuarioRepository();

        if (!usuarioRepository.existeAdmin()) {
            String email = System.getenv().getOrDefault("ADMIN_EMAIL", "admin@coffeegestao.com");
            String senha = System.getenv("ADMIN_PASSWORD");

            if (senha == null || senha.isBlank()) {
                System.out.println("Nenhum admin encontrado. Defina a variável de ambiente ADMIN_PASSWORD e rode novamente para criar o usuário administrador.");
                return;
            }

            Usuario admin = new Usuario("Administrador", email, senha, "ADMIN", true);
            usuarioRepository.salvar(admin);
            System.out.println("Usuário admin criado: " + email);
        }
    }
}
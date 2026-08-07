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
            Usuario admin = new Usuario("Administrador", "admin@coffeegestao.com", "admin123", "ADMIN", true);
            usuarioRepository.salvar(admin);
            System.out.println("Usuário admin criado: admin@coffeegestao.com / admin123");
        }
    }
}
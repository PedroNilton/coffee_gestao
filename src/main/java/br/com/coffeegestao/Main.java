package br.com.coffeegestao;

import br.com.coffeegestao.database.DatabaseInitializer;
import br.com.coffeegestao.view.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
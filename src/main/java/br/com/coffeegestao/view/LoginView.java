package br.com.coffeegestao.view;

import br.com.coffeegestao.controller.LoginController;
import br.com.coffeegestao.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private final LoginController loginController;

    private JTextField campoEmail;
    private JPasswordField campoSenha;

    public LoginView() {
        this.loginController = new LoginController();

        setTitle("Login - Coffee Gestão");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(criarPainelFormulario(), BorderLayout.CENTER);
        add(criarPainelBotao(), BorderLayout.SOUTH);
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridLayout(2, 2, 5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        campoEmail = new JTextField();
        campoSenha = new JPasswordField();

        painel.add(new JLabel("Email:"));
        painel.add(campoEmail);
        painel.add(new JLabel("Senha:"));
        painel.add(campoSenha);

        return painel;
    }

    private JPanel criarPainelBotao() {
        JPanel painel = new JPanel(new FlowLayout());

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener(e -> autenticar());

        painel.add(btnEntrar);
        return painel;
    }
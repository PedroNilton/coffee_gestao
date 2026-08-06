package br.com.coffeegestao.view;

import br.com.coffeegestao.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {

    private final Usuario usuarioLogado;

    public DashboardView(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("Dashboard - Coffee Gestão");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(criarPainelBoasVindas(), BorderLayout.NORTH);
        add(criarPainelMenu(), BorderLayout.CENTER);
        add(criarPainelSair(), BorderLayout.SOUTH);
    }

    private JPanel criarPainelBoasVindas() {
        JPanel painel = new JPanel();
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));

        JLabel label = new JLabel("Bem-vindo(a), " + usuarioLogado.getNome() + " (" + usuarioLogado.getPerfil() + ")");
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));

        painel.add(label);
        return painel;
    }

    private JPanel criarPainelMenu() {
        JPanel painel = new JPanel(new GridLayout(3, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JButton btnClientes = new JButton("Clientes");
        JButton btnAparelhos = new JButton("Aparelhos");
        JButton btnOrdensServico = new JButton("Ordens de Serviço");

        btnClientes.addActionListener(e -> new ClienteView().setVisible(true));
        btnAparelhos.addActionListener(e -> new AparelhoView().setVisible(true));
        btnOrdensServico.addActionListener(e -> new OrdemServicoView().setVisible(true));

        painel.add(btnClientes);
        painel.add(btnAparelhos);
        painel.add(btnOrdensServico);

        return painel;
    }

    private JPanel criarPainelSair() {
        JPanel painel = new JPanel(new FlowLayout());

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });

        painel.add(btnSair);
        return painel;
    }
}
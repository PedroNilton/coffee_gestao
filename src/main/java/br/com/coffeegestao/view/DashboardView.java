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
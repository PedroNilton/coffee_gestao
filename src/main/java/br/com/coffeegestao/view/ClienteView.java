package br.com.coffeegestao.view;

import br.com.coffeegestao.controller.ClienteController;
import br.com.coffeegestao.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteView extends JFrame {

    private final ClienteController clienteController;

    private JTextField campoNome;
    private JTextField campoTelefone;
    private JTextField campoCpf;
    private JTextField campoEmail;
    private JTextField campoEndereco;

    private JTable tabelaClientes;
    private DefaultTableModel tableModel;

    private Integer idSelecionado;

    public ClienteView() {
        this.clienteController = new ClienteController();

        setTitle("Clientes - Coffee Gestão");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);

        carregarClientes();
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridLayout(5, 2, 5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campoNome = new JTextField();
        campoTelefone = new JTextField();
        campoCpf = new JTextField();
        campoEmail = new JTextField();
        campoEndereco = new JTextField();

        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);
        painel.add(new JLabel("Telefone:"));
        painel.add(campoTelefone);
        painel.add(new JLabel("CPF:"));
        painel.add(campoCpf);
        painel.add(new JLabel("Email:"));
        painel.add(campoEmail);
        painel.add(new JLabel("Endereço:"));
        painel.add(campoEndereco);

        return painel;
    }

    private JScrollPane criarPainelTabela() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Telefone", "CPF", "Email", "Endereço"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaClientes = new JTable(tableModel);
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormularioComSelecao();
            }
        });

        return new JScrollPane(tabelaClientes);
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout());

        JButton btnNovo = new JButton("Novo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        btnNovo.addActionListener(e -> salvarCliente());
        btnSalvar.addActionListener(e -> atualizarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());
        btnLimpar.addActionListener(e -> limparFormulario());

        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);

        return painel;
    }

    private void salvarCliente() {
        try {
            clienteController.cadastrar(
                    campoNome.getText(),
                    campoTelefone.getText(),
                    campoCpf.getText(),
                    campoEmail.getText(),
                    campoEndereco.getText()
            );

            limparFormulario();
            carregarClientes();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarCliente() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Cliente cliente = new Cliente(
                    idSelecionado,
                    campoNome.getText(),
                    campoTelefone.getText(),
                    campoCpf.getText(),
                    campoEmail.getText(),
                    campoEndereco.getText()
            );

            clienteController.atualizar(cliente);
            limparFormulario();
            carregarClientes();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirCliente() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this, "Deseja realmente excluir este cliente?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            clienteController.remover(idSelecionado);
            limparFormulario();
            carregarClientes();
        }
    }

    private void preencherFormularioComSelecao() {
        int linha = tabelaClientes.getSelectedRow();
        if (linha == -1) return;

        idSelecionado = (Integer) tableModel.getValueAt(linha, 0);
        campoNome.setText((String) tableModel.getValueAt(linha, 1));
        campoTelefone.setText((String) tableModel.getValueAt(linha, 2));
        campoCpf.setText((String) tableModel.getValueAt(linha, 3));
        campoEmail.setText((String) tableModel.getValueAt(linha, 4));
        campoEndereco.setText((String) tableModel.getValueAt(linha, 5));
    }

    private void limparFormulario() {
        idSelecionado = null;
        campoNome.setText("");
        campoTelefone.setText("");
        campoCpf.setText("");
        campoEmail.setText("");
        campoEndereco.setText("");
        tabelaClientes.clearSelection();
    }

    private void carregarClientes() {
        tableModel.setRowCount(0);

        List<Cliente> clientes = clienteController.listarTodos();
        for (Cliente c : clientes) {
            tableModel.addRow(new Object[]{
                    c.getId(), c.getNome(), c.getTelefone(), c.getCpf(), c.getEmail(), c.getEndereco()
            });
        }
    }
}
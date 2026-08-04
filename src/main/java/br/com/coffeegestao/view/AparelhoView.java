package br.com.coffeegestao.view;

import br.com.coffeegestao.controller.AparelhoController;
import br.com.coffeegestao.controller.ClienteController;
import br.com.coffeegestao.model.Aparelho;
import br.com.coffeegestao.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AparelhoView extends JFrame {

    private final AparelhoController aparelhoController;
    private final ClienteController clienteController;

    private JComboBox<Cliente> comboCliente;
    private JTextField campoTipo;
    private JTextField campoMarca;
    private JTextField campoModelo;
    private JTextField campoNumeroSerie;
    private JTextField campoObservacoes;

    private JTable tabelaAparelhos;
    private DefaultTableModel tableModel;

    private Integer idSelecionado;

    public AparelhoView() {
        this.aparelhoController = new AparelhoController();
        this.clienteController = new ClienteController();

        setTitle("Aparelhos - Coffee Gestão");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);

        carregarAparelhos();
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridLayout(5, 2, 5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        comboCliente = new JComboBox<>();
        carregarClientesNoCombo();

        campoTipo = new JTextField();
        campoMarca = new JTextField();
        campoModelo = new JTextField();
        campoNumeroSerie = new JTextField();
        campoObservacoes = new JTextField();

        painel.add(new JLabel("Cliente:"));
        painel.add(comboCliente);
        painel.add(new JLabel("Tipo:"));
        painel.add(campoTipo);
        painel.add(new JLabel("Marca:"));
        painel.add(campoMarca);
        painel.add(new JLabel("Modelo:"));
        painel.add(campoModelo);
        painel.add(new JLabel("Nº de Série:"));
        painel.add(campoNumeroSerie);

        return painel;
    }

    private JScrollPane criarPainelTabela() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Cliente ID", "Tipo", "Marca", "Modelo", "Nº Série"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaAparelhos = new JTable(tableModel);
        tabelaAparelhos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormularioComSelecao();
            }
        });

        return new JScrollPane(tabelaAparelhos);
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout());

        JButton btnNovo = new JButton("Novo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        btnNovo.addActionListener(e -> salvarAparelho());
        btnSalvar.addActionListener(e -> atualizarAparelho());
        btnExcluir.addActionListener(e -> excluirAparelho());
        btnLimpar.addActionListener(e -> limparFormulario());

        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);

        return painel;
    }

    private void carregarClientesNoCombo() {
        comboCliente.removeAllItems();
        List<Cliente> clientes = clienteController.listarTodos();
        for (Cliente cliente : clientes) {
            comboCliente.addItem(cliente);
        }
    }

    private void salvarAparelho() {
        Cliente clienteSelecionado = (Cliente) comboCliente.getSelectedItem();
        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Cadastre um cliente antes de adicionar aparelhos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            aparelhoController.cadastrar(
                    clienteSelecionado.getId(),
                    campoTipo.getText(),
                    campoMarca.getText(),
                    campoModelo.getText(),
                    campoNumeroSerie.getText(),
                    campoObservacoes.getText()
            );

            limparFormulario();
            carregarAparelhos();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar aparelho.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarAparelho() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um aparelho na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente clienteSelecionado = (Cliente) comboCliente.getSelectedItem();
        if (clienteSelecionado == null) return;

        try {
            Aparelho aparelho = new Aparelho(
                    idSelecionado,
                    clienteSelecionado.getId(),
                    campoTipo.getText(),
                    campoMarca.getText(),
                    campoModelo.getText(),
                    campoNumeroSerie.getText(),
                    campoObservacoes.getText()
            );

            aparelhoController.atualizar(aparelho);
            limparFormulario();
            carregarAparelhos();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar aparelho.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirAparelho() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um aparelho na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this, "Deseja realmente excluir este aparelho?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            aparelhoController.remover(idSelecionado);
            limparFormulario();
            carregarAparelhos();
        }
    }

    private void preencherFormularioComSelecao() {
        int linha = tabelaAparelhos.getSelectedRow();
        if (linha == -1) return;

        idSelecionado = (Integer) tableModel.getValueAt(linha, 0);
        int clienteId = (Integer) tableModel.getValueAt(linha, 1);

        for (int i = 0; i < comboCliente.getItemCount(); i++) {
            if (comboCliente.getItemAt(i).getId() == clienteId) {
                comboCliente.setSelectedIndex(i);
                break;
            }
        }

        campoTipo.setText((String) tableModel.getValueAt(linha, 2));
        campoMarca.setText((String) tableModel.getValueAt(linha, 3));
        campoModelo.setText((String) tableModel.getValueAt(linha, 4));
        campoNumeroSerie.setText((String) tableModel.getValueAt(linha, 5));
    }

    private void limparFormulario() {
        idSelecionado = null;
        if (comboCliente.getItemCount() > 0) comboCliente.setSelectedIndex(0);
        campoTipo.setText("");
        campoMarca.setText("");
        campoModelo.setText("");
        campoNumeroSerie.setText("");
        campoObservacoes.setText("");
        tabelaAparelhos.clearSelection();
    }

    private void carregarAparelhos() {
        tableModel.setRowCount(0);

        List<Aparelho> aparelhos = aparelhoController.listarTodos();
        for (Aparelho a : aparelhos) {
            tableModel.addRow(new Object[]{
                    a.getId(), a.getClienteId(), a.getTipo(), a.getMarca(), a.getModelo(), a.getNumeroSerie()
            });
        }
    }
}
package br.com.coffeegestao.view;

import br.com.coffeegestao.controller.AparelhoController;
import br.com.coffeegestao.controller.ClienteController;
import br.com.coffeegestao.controller.OrdemServicoController;
import br.com.coffeegestao.model.Aparelho;
import br.com.coffeegestao.model.Cliente;
import br.com.coffeegestao.model.OrdemServico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrdemServicoView extends JFrame {

    private final OrdemServicoController ordemServicoController;
    private final ClienteController clienteController;
    private final AparelhoController aparelhoController;

    private JComboBox<Cliente> comboCliente;
    private JComboBox<Aparelho> comboAparelho;
    private JTextArea campoDefeitoRelatado;
    private JTextArea campoDiagnostico;
    private JTextArea campoSolucao;
    private JTextField campoValorServico;

    private JTable tabelaOrdens;
    private DefaultTableModel tableModel;

    private Integer idSelecionado;

    public OrdemServicoView() {
        this.ordemServicoController = new OrdemServicoController();
        this.clienteController = new ClienteController();
        this.aparelhoController = new AparelhoController();

        setTitle("Ordens de Serviço - Coffee Gestão");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);

        carregarOrdens();
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridLayout(6, 2, 5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        comboCliente = new JComboBox<>();
        comboCliente.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente cliente) {
                    setText(cliente.getNome());
                }
                return this;
            }
        });
        comboCliente.addActionListener(e -> carregarAparelhosDoCliente());
        carregarClientesNoCombo();

        comboAparelho = new JComboBox<>();
        comboAparelho.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Aparelho aparelho) {
                    setText(aparelho.getTipo() + " " + aparelho.getModelo());
                }
                return this;
            }
        });

        campoDefeitoRelatado = new JTextArea(2, 20);
        campoDiagnostico = new JTextArea(2, 20);
        campoSolucao = new JTextArea(2, 20);
        campoValorServico = new JTextField();

        painel.add(new JLabel("Cliente:"));
        painel.add(comboCliente);
        painel.add(new JLabel("Aparelho:"));
        painel.add(comboAparelho);
        painel.add(new JLabel("Defeito relatado:"));
        painel.add(new JScrollPane(campoDefeitoRelatado));
        painel.add(new JLabel("Diagnóstico:"));
        painel.add(new JScrollPane(campoDiagnostico));
        painel.add(new JLabel("Solução:"));
        painel.add(new JScrollPane(campoSolucao));
        painel.add(new JLabel("Valor do serviço:"));
        painel.add(campoValorServico);

        return painel;
    }

    private JScrollPane criarPainelTabela() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Cliente ID", "Aparelho ID", "Status", "Valor"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaOrdens = new JTable(tableModel);
        tabelaOrdens.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormularioComSelecao();
            }
        });

        return new JScrollPane(tabelaOrdens);
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout());

        JButton btnAbrir = new JButton("Abrir Ordem");
        JButton btnDiagnosticar = new JButton("Registrar Diagnóstico");
        JButton btnConcluir = new JButton("Concluir");
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnLimpar = new JButton("Limpar");

        btnAbrir.addActionListener(e -> abrirOrdem());
        btnDiagnosticar.addActionListener(e -> registrarDiagnostico());
        btnConcluir.addActionListener(e -> concluirOrdem());
        btnCancelar.addActionListener(e -> cancelarOrdem());
        btnLimpar.addActionListener(e -> limparFormulario());

        painel.add(btnAbrir);
        painel.add(btnDiagnosticar);
        painel.add(btnConcluir);
        painel.add(btnCancelar);
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

    private void carregarAparelhosDoCliente() {
        comboAparelho.removeAllItems();
        Cliente clienteSelecionado = (Cliente) comboCliente.getSelectedItem();
        if (clienteSelecionado == null) return;

        List<Aparelho> aparelhos = aparelhoController.listarPorClienteId(clienteSelecionado.getId());
        for (Aparelho aparelho : aparelhos) {
            comboAparelho.addItem(aparelho);
        }
    }

    private void abrirOrdem() {
        Cliente cliente = (Cliente) comboCliente.getSelectedItem();
        Aparelho aparelho = (Aparelho) comboAparelho.getSelectedItem();

        if (cliente == null || aparelho == null) {
            JOptionPane.showMessageDialog(this, "Selecione cliente e aparelho.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ordemServicoController.abrir(cliente.getId(), aparelho.getId(), campoDefeitoRelatado.getText());
            limparFormulario();
            carregarOrdens();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir ordem de serviço.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarDiagnostico() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma ordem na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ordemServicoController.registrarDiagnostico(idSelecionado, campoDiagnostico.getText());
            limparFormulario();
            carregarOrdens();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void concluirOrdem() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma ordem na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double valor = Double.parseDouble(campoValorServico.getText().replace(",", "."));
            ordemServicoController.concluir(idSelecionado, campoSolucao.getText(), valor);
            limparFormulario();
            carregarOrdens();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor do serviço inválido.", "Erro", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalStateException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Não foi possível concluir", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao concluir ordem de serviço.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarOrdem() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma ordem na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this, "Deseja realmente cancelar essa ordem?", "Confirmar cancelamento", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                ordemServicoController.cancelar(idSelecionado);
                limparFormulario();
                carregarOrdens();
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Não foi possível cancelar", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
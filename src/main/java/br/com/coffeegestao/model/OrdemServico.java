package br.com.coffeegestao.model;

import java.time.LocalDateTime;

public class OrdemServico {

    private int id;
    private int clienteId;
    private int aparelhoId;
    private String defeitoRelatado;
    private String diagnostico;
    private String solucao;
    private StatusOrdemServico status;
    private double valorServico;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;

    public OrdemServico() {
    }

    public OrdemServico(int id, int clienteId, int aparelhoId, String defeitoRelatado, String diagnostico,
                        String solucao, StatusOrdemServico status, double valorServico,
                        LocalDateTime dataAbertura, LocalDateTime dataFechamento) {
        this.id = id;
        this.clienteId = clienteId;
        this.aparelhoId = aparelhoId;
        this.defeitoRelatado = defeitoRelatado;
        this.diagnostico = diagnostico;
        this.solucao = solucao;
        this.status = status;
        this.valorServico = valorServico;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
    }

    public OrdemServico(int clienteId, int aparelhoId, String defeitoRelatado) {
        this.clienteId = clienteId;
        this.aparelhoId = aparelhoId;
        this.defeitoRelatado = defeitoRelatado;
        this.status = StatusOrdemServico.ABERTA;
        this.dataAbertura = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getAparelhoId() {
        return aparelhoId;
    }

    public String getDefeitoRelatado() {
        return defeitoRelatado;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getSolucao() {
        return solucao;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public double getValorServico() {
        return valorServico;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setAparelhoId(int aparelhoId) {
        this.aparelhoId = aparelhoId;
    }

    public void setDefeitoRelatado(String defeitoRelatado) {
        this.defeitoRelatado = defeitoRelatado;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public void setStatus(StatusOrdemServico status) {
        this.status = status;
    }

    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    @Override
    public String toString() {
        return "OrdemServico{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", aparelhoId=" + aparelhoId +
                ", status=" + status +
                ", valorServico=" + valorServico +
                '}';
    }
}
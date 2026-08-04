package br.com.coffeegestao.model;

public class Aparelho {

    private int id;
    private int clienteId;
    private String tipo;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String observacoes;

    public Aparelho() {
    }

    public Aparelho(int id, int clienteId, String tipo, String marca, String modelo, String numeroSerie, String observacoes) {
        this.id = id;
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.observacoes = observacoes;
    }

    public Aparelho(int clienteId, String tipo, String marca, String modelo, String numeroSerie, String observacoes) {
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return "Aparelho{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", tipo='" + tipo + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", numeroSerie='" + numeroSerie + '\'' +
                ", observacoes='" + observacoes + '\'' +
                '}';
    }
}
package model;

public class Item {
    private String nome;
    private String tipo;
    private int valorEfeito;

    public Item(String nome, String tipo, int valorEfeito) {
        this.nome = nome;
        this.tipo = tipo;
        this.valorEfeito = valorEfeito;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getValorEfeito() {
        return valorEfeito;
    }
}

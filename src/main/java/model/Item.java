package model;

public abstract class Item {
    private String nome;
    private int valorEfeito;

    public Item(String nome, int valorEfeito) {
        this.nome = nome;
        this.valorEfeito = valorEfeito;
    }

    public String getNome() {
        return nome;
    }

    public int getValorEfeito() {
        return valorEfeito;
    }

    // O método 'usar' agora é abstrato. Cada tipo de item terá sua própria implementação.
    public abstract void usar(Criatura criatura);
}

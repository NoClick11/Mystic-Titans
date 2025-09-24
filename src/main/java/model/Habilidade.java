package model;

public class Habilidade {
    private String nome;
    private int danoBase;
    private int custoMana;


    public Habilidade(String nome, int danoBase, int custoMana) {
        this.nome = nome;
        this.danoBase = danoBase;
        this.custoMana = custoMana;
    }

    public String getNome() {
        return nome;
    }

    public int getDanoBase() {
        return danoBase;
    }

    public int getCustoMana() {
        return custoMana;
    }
}

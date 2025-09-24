package model;

public class Habilidade {
    private String nome;
    private int danoBase;
    private int custoMana;
    private EfeitoStatus efeito;


    public Habilidade(String nome, int danoBase, int custoMana, EfeitoStatus efeito) {
        this.nome = nome;
        this.danoBase = danoBase;
        this.custoMana = custoMana;
        this.efeito = efeito;
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

    public EfeitoStatus getEfeito() {
        return efeito;
    }
}

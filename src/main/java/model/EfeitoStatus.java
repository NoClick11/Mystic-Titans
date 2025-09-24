package model;

public abstract class EfeitoStatus {
    private String nome;
    private int duracao;

    public EfeitoStatus(String nome, int duracao) {
        this.nome = nome;
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    // Método abstrato que cada subclasse deve implementar
    public abstract void aplicarEfeito(Criatura criatura);
}

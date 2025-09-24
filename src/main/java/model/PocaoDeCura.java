package model;

public class PocaoDeCura extends Item {
    private int hpMaximo;

    public PocaoDeCura(String nome, int valorEfeito, int hpMaximo) {
        super(nome, valorEfeito);
        this.hpMaximo = hpMaximo;
    }

    @Override
    public void usar(Criatura criatura) {
        int novoHp = criatura.getHp() + this.getValorEfeito();
        criatura.setHp(Math.min(novoHp, hpMaximo));
        System.out.println(criatura.getNome() + " usou " + this.getNome() + " e curou " + this.getValorEfeito() + " HP!");
    }
}

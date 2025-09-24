package model;

public class PocaoDeAtaque extends Item {

    public PocaoDeAtaque(String nome, int valorEfeito) {
        super(nome, valorEfeito);
    }

    @Override
    public void usar(Criatura criatura) {
        int novoAtk = criatura.getAtk() + this.getValorEfeito();
        criatura.setAtk(novoAtk);
        System.out.println(criatura.getNome() + " usou " + this.getNome() + " e seu ataque aumentou em " + this.getValorEfeito() + "!");
    }
}

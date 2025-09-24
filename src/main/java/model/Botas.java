package model;

public class Botas extends Item {

    public Botas(String nome, int valorEfeito) {
        super(nome, valorEfeito);
    }

    @Override
    public void usar(Criatura criatura) {
        int novaVelocidade = criatura.getVelocidade() + this.getValorEfeito();
        criatura.setVelocidade(novaVelocidade);
        System.out.println(criatura.getNome() + " usou " + this.getNome() + " e sua velocidade aumentou em " + this.getValorEfeito() + "!");
    }
}

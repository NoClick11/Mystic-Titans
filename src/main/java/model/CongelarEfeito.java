package habilidades;

import model.Criatura;
import model.EfeitoStatus;

public class CongelarEfeito extends EfeitoStatus {

    public CongelarEfeito(int duracao) {
        super("Congelado", duracao);
    }

    @Override
    public void aplicarEfeito(Criatura criatura) {
        System.out.println(criatura.getNome() + " está congelado e não pode se mover!");
        criatura.setPodeAgir(false);
    }
}

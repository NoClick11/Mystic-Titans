package model;

import model.Criatura;
import model.EfeitoStatus;

public class EnvenenarEfeito extends EfeitoStatus {
    private int danoPorTurno;

    public EnvenenarEfeito(int duracao, int danoPorTurno) {
        super("Envenenado", duracao);
        this.danoPorTurno = danoPorTurno;
    }

    @Override
    public void aplicarEfeito(Criatura criatura) {
        System.out.println(criatura.getNome() + " está envenenado e perde " + danoPorTurno + " HP!");
        criatura.setHp(criatura.getHp() - danoPorTurno);
    }
}

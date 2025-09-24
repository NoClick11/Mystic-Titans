package habilidades;

import model.Criatura;
import model.EfeitoStatus;

public class QueimarEfeito extends EfeitoStatus {
    private int danoPorTurno;

    public QueimarEfeito(int duracao, int danoPorTurno) {
        super("Queimado", duracao);
        this.danoPorTurno = danoPorTurno;
    }

    @Override
    public void aplicarEfeito(Criatura criatura) {
        System.out.println(criatura.getNome() + " está queimando e perde " + danoPorTurno + " HP!");
        criatura.setHp(criatura.getHp() - danoPorTurno);
    }
}

package service;
import model.Criatura;
import model.EfeitoStatus;

import java.util.Iterator;

public class GerenciadorEfeitosService {

    public void aplicarEfeitosDoTurno(Criatura criatura) {
        criatura.setPodeAgir(true); // Reseta o estado antes de verificar os efeitos
        Iterator<EfeitoStatus> iterator = criatura.getEfeitos().iterator();
        while (iterator.hasNext()) {
            EfeitoStatus efeito = iterator.next();

            efeito.aplicarEfeito(criatura);

            efeito.setDuracao(efeito.getDuracao() - 1);
            if (efeito.getDuracao() <= 0) {
                System.out.println(criatura.getNome() + " não está mais " + efeito.getNome().toLowerCase() + ".");
                iterator.remove();
            }
        }
    }
}


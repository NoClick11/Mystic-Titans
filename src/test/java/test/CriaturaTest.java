package test;

import model.Criatura;
import model.TipoElemental;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CriaturaTest {

    @Test
    public void deveCriarCriaturaComAtributosCorretos() {
        // Cenário: Criar uma criatura com nome, HP, ATK, DEF, velocidade e tipo.
        Criatura criatura = new Criatura("Dragão", 100, 20, 15, 30, TipoElemental.FOGO);

        // Verificação: Checar se os atributos foram atribuídos corretamente.
        assertEquals("Dragão", criatura.getNome());
        assertEquals(100, criatura.getHp());
        assertEquals(20, criatura.getAtk());
        assertEquals(15, criatura.getDef());
        assertEquals(30, criatura.getVelocidade());
        assertEquals(TipoElemental.FOGO, criatura.getTipo());
    }

    @Test
    public void deveReduzirHpCorretamente() {
        // Cenário: Uma criatura recebe dano.
        // Adicionada a velocidade para o construtor.
        Criatura criatura = new Criatura("Golem", 50, 10, 20, 10, TipoElemental.TERRA);

        // Ação: Reduzir o HP.
        criatura.setHp(criatura.getHp() - 10);

        // Verificação: Checar se o HP foi atualizado.
        assertEquals(40, criatura.getHp());
    }
}

package test;

import habilidades.CongelarEfeito;
import habilidades.EnvenenarEfeito;
import habilidades.QueimarEfeito;
import model.Criatura;
import model.EfeitoStatus;
import model.TipoElemental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import service.GerenciadorEfeitosService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Esta anotação ativa as funcionalidades do Mockito para os testes.
@ExtendWith(MockitoExtension.class)
class GerenciadorEfeitosTest {

    @Mock
    private Criatura criaturaMock;

    @Mock
    private EfeitoStatus efeitoMock;

    @InjectMocks
    private GerenciadorEfeitosService gerenciadorEfeitos;

    @Test
    void deveAplicarDanoDeEfeitoEDecrementarDuracao() {
        // Cenário: Uma criatura tem um efeito ativo
        List<EfeitoStatus> efeitos = new ArrayList<>();
        efeitos.add(efeitoMock);

        // AÇÃO: Configurando o mock para que o método getEfeitos()
        // retorne a lista de efeitos.
        when(criaturaMock.getEfeitos()).thenReturn(efeitos);

        // AÇÃO: Configurando o mock para que o método getNome()
        // retorne um valor não nulo.
        when(efeitoMock.getNome()).thenReturn("Queimado");

        // Ação: O Gerenciador de Efeitos aplica os efeitos do turno
        gerenciadorEfeitos.aplicarEfeitosDoTurno(criaturaMock);

        // Verificação:
        verify(efeitoMock, times(1)).aplicarEfeito(criaturaMock);
        verify(efeitoMock, times(1)).setDuracao(anyInt());
    }

    @Test
    void deveAplicarDanoDeQueimar() {
        Criatura criatura = new Criatura("Criatura de Terra", 100, 10, 5, 10, TipoElemental.TERRA);
        EfeitoStatus efeitoQueimar = new QueimarEfeito(3, 5);
        criatura.aplicarEfeito(efeitoQueimar);
        assertEquals(1, criatura.getEfeitos().size());

        gerenciadorEfeitos.aplicarEfeitosDoTurno(criatura);

        assertEquals(95, criatura.getHp());
        assertEquals(2, criatura.getEfeitos().get(0).getDuracao());
    }

    @Test
    void deveImpedirAcaoQuandoCriaturaEstiverCongelada() {
        Criatura criatura = new Criatura("Criatura de Água", 100, 10, 5, 10, TipoElemental.AGUA);
        EfeitoStatus efeitoCongelar = new CongelarEfeito(2);
        criatura.aplicarEfeito(efeitoCongelar);

        assertTrue(criatura.podeAgir());
        gerenciadorEfeitos.aplicarEfeitosDoTurno(criatura);

        assertFalse(criatura.podeAgir());
        assertEquals(1, criatura.getEfeitos().get(0).getDuracao());
    }

    @Test
    void deveAplicarDanoDeVeneno() {
        // Cenário: Uma criatura é envenenada por 3 turnos
        Criatura criatura = new Criatura("Criatura da Floresta", 80, 15, 8, 12, TipoElemental.TERRA);
        EfeitoStatus efeitoEnvenenar = new EnvenenarEfeito(3, 8); // 3 turnos, 8 de dano por turno

        criatura.aplicarEfeito(efeitoEnvenenar);
        assertEquals(1, criatura.getEfeitos().size());

        // Ação: Aplica o efeito no primeiro turno
        gerenciadorEfeitos.aplicarEfeitosDoTurno(criatura);

        // Verificação: O HP deve ter diminuído pelo dano de veneno
        assertEquals(72, criatura.getHp());
        assertEquals(2, criatura.getEfeitos().get(0).getDuracao());
    }
}
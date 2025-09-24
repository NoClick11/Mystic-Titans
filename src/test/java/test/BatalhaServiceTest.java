package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.BatalhaService;
import service.CalculadoraElementalService;
import service.GerenciadorEfeitosService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BatalhaServiceTest {

    @Mock
    private CalculadoraElementalService calculadoraElemental;

    @Mock
    private GerenciadorEfeitosService gerenciadorEfeitos;

    @InjectMocks
    private BatalhaService batalhaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveEncerrarBatalhaQuandoCriaturaMorre() {
        Criatura criaturaVeloz = new Criatura("Veloz", 10, 15, 5, 20, 50, 50, TipoElemental.FOGO, new ArrayList<>());
        Criatura criaturaLenta = new Criatura("Lenta", 10, 15, 5, 10, 50, 50, TipoElemental.AGUA, new ArrayList<>());

        when(calculadoraElemental.calcularDano(anyInt(), anyInt(), any(), any())).thenReturn(10.0);

        batalhaService.iniciarBatalha(criaturaVeloz, criaturaLenta);

        verify(calculadoraElemental, atLeast(1)).calcularDano(anyInt(), anyInt(), any(), any());

        assertTrue(criaturaVeloz.getHp() <= 0 || criaturaLenta.getHp() <= 0);
    }

    @Test
    void deveCurarCriaturaQuandoUsarPocaoDeVida() {
        Criatura criatura = new Criatura("Criatura Ferida", 30, 10, 5, 15, 50, 50, TipoElemental.AGUA, new ArrayList<>());

        // Criamos uma instância real da poção de cura
        Item pocaoDeVida = new PocaoDeCura("Poção de Vida", 50, 100);

        criatura.getInventario().adicionarItem(pocaoDeVida);
        assertEquals(30, criatura.getHp());
        batalhaService.usarItem(criatura, pocaoDeVida);
        assertEquals(80, criatura.getHp());
    }

    @Test
    void deveAumentarAtaqueQuandoUsarPocaoDeFuria() {
        Criatura criatura = new Criatura("Guerreiro", 100, 10, 5, 20, 50, 50, TipoElemental.TERRA, new ArrayList<>());

        // Criamos uma instância real da poção de ataque
        Item pocaoDeFuria = new PocaoDeAtaque("Poção de Fúria", 15);

        criatura.getInventario().adicionarItem(pocaoDeFuria);
        assertEquals(10, criatura.getAtk());
        batalhaService.usarItem(criatura, pocaoDeFuria);
        assertEquals(25, criatura.getAtk());
    }

    @Test
    void deveAtacarPrimeiroQuemTiverMaiorVelocidade() {
        Criatura criaturaA = new Criatura("Veloz", 100, 20, 10, 30, 50, 50, TipoElemental.AR, new ArrayList<>());
        Criatura criaturaB = new Criatura("Lento", 100, 20, 10, 10, 50, 50, TipoElemental.TERRA, new ArrayList<>());

        when(calculadoraElemental.calcularDano(anyInt(), anyInt(), any(), any())).thenReturn(50.0);
        batalhaService.iniciarBatalha(criaturaA, criaturaB);

        verify(calculadoraElemental, atLeast(1)).calcularDano(criaturaA.getAtk(), criaturaB.getDef(), criaturaA.getTipo(), criaturaB.getTipo());
        verify(calculadoraElemental, atLeast(1)).calcularDano(criaturaB.getAtk(), criaturaA.getDef(), criaturaB.getTipo(), criaturaA.getTipo());
    }

    @Test
    void deveAplicarEfeitoDeQueimarAoUsarHabilidade() {
        Habilidade habilidadeQueimar = new Habilidade("Ataque de Fogo", 15, 10, new QueimarEfeito(2, 5));
        Criatura atacante = new Criatura("Dragão", 100, 20, 10, 30, 50, 50, TipoElemental.FOGO, List.of(habilidadeQueimar));
        Criatura defensor = new Criatura("Golem", 100, 15, 12, 10, 50, 50, TipoElemental.TERRA, new ArrayList<>());

        when(calculadoraElemental.calcularDano(anyInt(), anyInt(), any(), any())).thenReturn(101.0);
        batalhaService.iniciarBatalha(atacante, defensor);

        assertEquals(1, defensor.getEfeitos().size());
        assertEquals("Queimado", defensor.getEfeitos().get(0).getNome());
    }

    @Test
    void deveCongelarCriaturaAoUsarHabilidade() {
        Habilidade habilidadeCongelar = new Habilidade("Raio de Gelo", 10, 15, new CongelarEfeito(1));
        Criatura atacante = new Criatura("Mago", 100, 10, 5, 25, 50, 50, TipoElemental.AGUA, List.of(habilidadeCongelar));
        Criatura defensor = new Criatura("Viking", 100, 20, 10, 15, 50, 50, TipoElemental.FOGO, new ArrayList<>());

        when(calculadoraElemental.calcularDano(anyInt(), anyInt(), any(), any())).thenReturn(101.0);
        batalhaService.iniciarBatalha(atacante, defensor);

        assertEquals(1, defensor.getEfeitos().size());
        assertEquals("Congelado", defensor.getEfeitos().get(0).getNome());
    }

    @Test
    void deveAplicarEfeitoDeVenenoAoUsarHabilidade() {
        Habilidade habilidadeVeneno = new Habilidade("Nuvem Tóxica", 10, 8, new EnvenenarEfeito(3, 3));
        Criatura atacante = new Criatura("Ninja", 100, 15, 8, 40, 50, 50, TipoElemental.TREVAS, List.of(habilidadeVeneno));
        Criatura defensor = new Criatura("Guerreiro", 100, 20, 15, 20, 50, 50, TipoElemental.TERRA, new ArrayList<>());

        when(calculadoraElemental.calcularDano(anyInt(), anyInt(), any(), any())).thenReturn(101.0);
        batalhaService.iniciarBatalha(atacante, defensor);

        assertEquals(1, defensor.getEfeitos().size());
        assertEquals("Envenenado", defensor.getEfeitos().get(0).getNome());
    }

    @Test
    void deveAumentarVelocidadeQuandoEquiparBotas() {
        // Cenário: Criatura com velocidade inicial de 20.
        Criatura criatura = new Criatura("Guerreiro", 100, 10, 5, 20, 50, 50, TipoElemental.TERRA, new ArrayList<>());

        // Item que aumenta a velocidade em 15.
        Item botasVelocidade = new Botas("Botas da Velocidade", 15);

        // Adiciona as botas ao inventário da criatura.
        criatura.getInventario().adicionarItem(botasVelocidade);

        // Verificação: Checar a velocidade inicial.
        assertEquals(20, criatura.getVelocidade(), "A velocidade inicial deve ser 20.");

        // Ação: Usar o item na criatura.
        batalhaService.usarItem(criatura, botasVelocidade);

        // Verificação: Checar se a velocidade foi aumentada e o item foi removido.
        assertEquals(35, criatura.getVelocidade(), "A velocidade deve ter sido aumentada para 35.");
        assertFalse(criatura.getInventario().getItens().contains(botasVelocidade), "O item deve ter sido removido do inventário.");
    }
}

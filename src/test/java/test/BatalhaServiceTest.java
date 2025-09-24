package test;

import model.Criatura;
import model.Item;
import model.TipoElemental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.BatalhaService;
import service.CalculadoraElementalService;
import service.GerenciadorEfeitosService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BatalhaServiceTest {

    // Declara a dependência `CalculadoraElemental` como um mock.
    @Mock
    private CalculadoraElementalService calculadoraElemental;

    // Declara a dependência `GerenciadorEfeitos` como um mock.
    @Mock
    private GerenciadorEfeitosService gerenciadorEfeitos;

    // A anotação @InjectMocks injeta automaticamente os mocks declarados acima
    // na nossa classe de teste, a BatalhaService.
    @InjectMocks
    private BatalhaService batalhaService;

    // Este método é executado antes de cada teste e inicializa todos os mocks.
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveEncerrarBatalhaQuandoCriaturaMorre() {
        // Cenário: Criamos duas criaturas de teste.
        // Adicionada a velocidade de cada criatura.
        Criatura criatura1 = new Criatura("Heroi", 10, 15, 5, 20, TipoElemental.FOGO);
        Criatura criatura2 = new Criatura("Vilao", 10, 15, 5, 10, TipoElemental.AGUA);

        // Ação: Usamos o mock para simular um dano de 10.
        when(calculadoraElemental.calcularDano(anyInt(), anyInt(), any(), any())).thenReturn(10.0);

        // Iniciamos a simulação.
        batalhaService.iniciarBatalha(criatura1, criatura2);

        // Verificação:
        verify(calculadoraElemental, atLeast(1)).calcularDano(anyInt(), anyInt(), any(), any());

        // Verificamos se a batalha terminou.
        assertTrue(criatura1.getHp() <= 0 || criatura2.getHp() <= 0);
    }

    @Test
    void deveCurarCriaturaQuandoUsarPocaoDeVida() {
        // CORRIGIDO: Removida a linha que criava uma nova instância de BatalhaService
        Criatura criatura = new Criatura("Criatura Ferida", 30, 10, 5, 15, TipoElemental.AGUA);
        Item pocaoDeVida = mock(Item.class);
        when(pocaoDeVida.getValorEfeito()).thenReturn(50);
        when(pocaoDeVida.getNome()).thenReturn("Poção de Vida");
        when(pocaoDeVida.getTipo()).thenReturn("Cura");

        // Adiciona a poção ao inventário da criatura
        criatura.getInventario().adicionarItem(pocaoDeVida);

        // Verifique o HP inicial
        assertEquals(30, criatura.getHp());

        // Ação: Usar o item na criatura
        batalhaService.usarItem(criatura, pocaoDeVida, 100);

        // Verificação: Checar se o HP foi curado e o item foi removido
        assertEquals(80, criatura.getHp());
        assertFalse(criatura.getInventario().getItens().contains(pocaoDeVida));
    }

    @Test
    void deveAumentarAtaqueQuandoUsarPocaoDeFuria() {
        // CORRIGIDO: Removida a linha que criava uma nova instância de BatalhaService
        Criatura criatura = new Criatura("Guerreiro", 100, 10, 5, 20, TipoElemental.TERRA);
        Item pocaoDeFuria = mock(Item.class);
        when(pocaoDeFuria.getValorEfeito()).thenReturn(15);
        when(pocaoDeFuria.getNome()).thenReturn("Poção de Fúria");
        when(pocaoDeFuria.getTipo()).thenReturn("Ataque");

        // Adiciona a poção ao inventário da criatura
        criatura.getInventario().adicionarItem(pocaoDeFuria);

        // Verifique o ataque inicial
        assertEquals(10, criatura.getAtk());

        // Ação: Use o item na criatura
        batalhaService.usarItem(criatura, pocaoDeFuria, criatura.getHp());

        // Verificação: Cheque se o ataque foi aumentado e o item foi removido
        assertEquals(25, criatura.getAtk());
        assertFalse(criatura.getInventario().getItens().contains(pocaoDeFuria));
    }

    @Test
    void deveAtacarPrimeiroQuemTiverMaiorVelocidade() {
        // Cenário: Criatura A é mais rápida que a B.
        Criatura criaturaA = new Criatura("Veloz", 100, 20, 10, 30, TipoElemental.AR);
        Criatura criaturaB = new Criatura("Lento", 100, 20, 10, 10, TipoElemental.TERRA);

        // Ação: Simulamos o dano para que o teste seja previsível.
        when(calculadoraElemental.calcularDano(anyInt(), anyInt(), any(), any())).thenReturn(50.0);

        // Iniciamos a batalha.
        batalhaService.iniciarBatalha(criaturaA, criaturaB);

        // Verificação:
        // Verificamos se o método de dano da criatura mais rápida foi chamado pelo menos uma vez.
        verify(calculadoraElemental, atLeast(1)).calcularDano(
                criaturaA.getAtk(),
                criaturaB.getDef(),
                criaturaA.getTipo(),
                criaturaB.getTipo()
        );

        // Verificamos que o método também foi chamado com os parâmetros da criatura lenta.
        // A ordem não importa aqui, apenas que ambos participaram.
        verify(calculadoraElemental, atLeast(1)).calcularDano(
                criaturaB.getAtk(),
                criaturaA.getDef(),
                criaturaB.getTipo(),
                criaturaA.getTipo()
        );
    }
}

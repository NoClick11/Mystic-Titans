package test;


import model.TipoElemental;
import org.junit.jupiter.api.Test;
import service.CalculadoraElementalService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CalculadoraElementalTest {

    private final CalculadoraElementalService calculadora = new CalculadoraElementalService();

    @Test
    public void deveAplicarMultiplicadorDeVantagem() {
        // Cenário: Um ataque de Fogo (forte) contra uma criatura de Terra
        double dano = calculadora.calcularDano(20, 10, TipoElemental.FOGO, TipoElemental.TERRA);

        // Verificação: O dano deve ser o dobro (20 - 10) * 2.0 = 20
        // Adicionando o 'delta' de 0.01
        assertEquals(20.0, dano, 0.01);
    }

    @Test
    public void deveAplicarMultiplicadorDeDesvantagem() {
        // Cenário: Um ataque de Fogo (fraco) contra uma criatura de Água
        double dano = calculadora.calcularDano(20, 10, TipoElemental.FOGO, TipoElemental.AGUA);

        // Verificação: O dano deve ser metade (20 - 10) * 0.5 = 5
        // Adicionando o 'delta' de 0.01
        assertEquals(5.0, dano, 0.01);
    }

    @Test
    public void deveCalcularDanoNormal() {
        // Cenário: Um ataque sem vantagem ou desvantagem
        double dano = calculadora.calcularDano(20, 10, TipoElemental.FOGO, TipoElemental.AR);

        // Verificação: O dano deve ser o normal (20 - 10) * 1.0 = 10
        // Adicionando o 'delta' de 0.01
        assertEquals(10.0, dano, 0.01);
    }

    @Test
    public void deveRetornarZeroQuandoDanoForNegativo() {
        // Cenário: Ataque (5) é menor que Defesa (20). O dano seria negativo.
        double dano = calculadora.calcularDano(5, 20, TipoElemental.FOGO, TipoElemental.AR);
        // Esperamos que o dano não seja negativo, mas sim 0.
        assertTrue(dano >= 0);
    }

    @Test
    public void deveRetornarZeroQuandoDanoForZero() {
        // Cenário: Ataque é igual à Defesa
        double dano = calculadora.calcularDano(15, 15, TipoElemental.TERRA, TipoElemental.AR);
        assertEquals(0.0, dano, 0.01);
    }
}

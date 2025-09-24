package service;

import model.Criatura;
import model.Item;

public class BatalhaService {

    // Dependências de outros serviços
    private CalculadoraElementalService calculadoraElemental;
    private GerenciadorEfeitosService gerenciadorEfeitos;

    // Construtor para injetar as dependências
    public BatalhaService(CalculadoraElementalService calculadoraElemental, GerenciadorEfeitosService gerenciadorEfeitos) {
        this.calculadoraElemental = calculadoraElemental;
        this.gerenciadorEfeitos = gerenciadorEfeitos;
    }

    /**
     * Simula uma batalha por turnos entre duas criaturas.
     * @param criatura1 A primeira criatura.
     * @param criatura2 A segunda criatura.
     */
    public void iniciarBatalha(Criatura criatura1, Criatura criatura2) {
        System.out.println("--- Batalha entre " + criatura1.getNome() + " e " + criatura2.getNome() + " ---");

        // --- Lógica de Iniciativa por Velocidade ---
        Criatura atacante, defensor;
        if (criatura1.getVelocidade() >= criatura2.getVelocidade()) {
            atacante = criatura1;
            defensor = criatura2;
            System.out.println(criatura1.getNome() + " é mais rápido e ataca primeiro!");
        } else {
            atacante = criatura2;
            defensor = criatura1;
            System.out.println(criatura2.getNome() + " é mais rápido e ataca primeiro!");
        }
        // --- Fim da Lógica de Iniciativa ---

        int turno = 1;

        while (criatura1.getHp() > 0 && criatura2.getHp() > 0) {
            System.out.println("\n--- Turno " + turno + " ---");

            // Aplica os efeitos de status no início do turno,
            // e eles podem impedir a criatura de agir (ex: congelar)
            gerenciadorEfeitos.aplicarEfeitosDoTurno(atacante);

            // Verifica se a criatura pode agir antes de atacar
            if (atacante.podeAgir()) {
                // Calcula o dano do ataque e aplica ao defensor
                double dano = calculadoraElemental.calcularDano(
                        atacante.getAtk(),
                        defensor.getDef(),
                        atacante.getTipo(),
                        defensor.getTipo()
                );
                defensor.setHp(defensor.getHp() - (int) dano);

                System.out.println(atacante.getNome() + " ataca " + defensor.getNome() + " causando " + (int) dano + " de dano!");
            } else {
                System.out.println(atacante.getNome() + " não pode atacar neste turno!");
            }

            System.out.println(defensor.getNome() + " HP: " + defensor.getHp() + " | " + atacante.getNome() + " HP: " + atacante.getHp());

            // Inverte o papel dos combatentes para o próximo turno
            Criatura temp = atacante;
            atacante = defensor;
            defensor = temp;

            turno++;

            // Condição de fim da batalha
            if (criatura1.getHp() <= 0) {
                System.out.println("\n" + criatura2.getNome() + " vence a batalha!");
            } else if (criatura2.getHp() <= 0) {
                System.out.println("\n" + criatura1.getNome() + " vence a batalha!");
            }
        }
        System.out.println("--- Batalha finalizada! ---");
    }

    /**
     * Aplica o efeito de um item em uma criatura.
     * @param criatura A criatura que usará o item.
     * @param item O item a ser usado.
     * @param hpMaximo O HP máximo da criatura.
     */

    public void usarItem(Criatura criatura, Item item, int hpMaximo) {
        criatura.getInventario().usarItem(item, criatura, hpMaximo);
    }
}

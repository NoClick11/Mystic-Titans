package service;

import model.Criatura;
import model.Habilidade;
import model.Item;

public class BatalhaService {

    private CalculadoraElementalService calculadoraElemental;
    private GerenciadorEfeitosService gerenciadorEfeitos;

    public BatalhaService(CalculadoraElementalService calculadoraElemental, GerenciadorEfeitosService gerenciadorEfeitos) {
        this.calculadoraElemental = calculadoraElemental;
        this.gerenciadorEfeitos = gerenciadorEfeitos;
    }

    public void iniciarBatalha(Criatura criatura1, Criatura criatura2) {
        System.out.println("--- Batalha entre " + criatura1.getNome() + " e " + criatura2.getNome() + " ---");

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

        int turno = 1;

        while (criatura1.getHp() > 0 && criatura2.getHp() > 0) {
            System.out.println("\n--- Turno " + turno + " ---");
            gerenciadorEfeitos.aplicarEfeitosDoTurno(atacante);

            if (atacante.podeAgir()) {
                // --- Nova Lógica: Usar a primeira habilidade da criatura ---
                if (!atacante.getHabilidades().isEmpty()) {
                    Habilidade habilidade = atacante.getHabilidades().get(0);
                    if (atacante.getMana() >= habilidade.getCustoMana()) {
                        atacante.setMana(atacante.getMana() - habilidade.getCustoMana());

                        double dano = calculadoraElemental.calcularDano(
                                habilidade.getDanoBase(),
                                defensor.getDef(),
                                atacante.getTipo(),
                                defensor.getTipo()
                        );
                        defensor.setHp(defensor.getHp() - (int) dano);

                        System.out.println(atacante.getNome() + " usa " + habilidade.getNome() + " causando " + (int) dano + " de dano!");

                        if (habilidade.getEfeito() != null) {
                            defensor.aplicarEfeito(habilidade.getEfeito());
                        }

                    } else {
                        System.out.println(atacante.getNome() + " não tem mana suficiente para usar a habilidade!");
                    }
                } else {
                    // Lógica de ataque básico se não houver habilidades
                    double dano = calculadoraElemental.calcularDano(atacante.getAtk(), defensor.getDef(), atacante.getTipo(), defensor.getTipo());
                    defensor.setHp(defensor.getHp() - (int) dano);
                    System.out.println(atacante.getNome() + " realiza um ataque básico causando " + (int) dano + " de dano!");
                }
            } else {
                System.out.println(atacante.getNome() + " não pode atacar neste turno!");
            }

            System.out.println(defensor.getNome() + " HP: " + defensor.getHp() + " | " + atacante.getNome() + " HP: " + atacante.getHp());

            Criatura temp = atacante;
            atacante = defensor;
            defensor = temp;
            turno++;

            if (criatura1.getHp() <= 0 || criatura2.getHp() <= 0) {
                System.out.println("\n--- Batalha finalizada! ---");
                if (criatura1.getHp() <= 0) System.out.println(criatura2.getNome() + " vence a batalha!");
                else System.out.println(criatura1.getNome() + " vence a batalha!");
            }
        }
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

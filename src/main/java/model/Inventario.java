package model;

import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private List<Item> itens;

    public Inventario() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Item item) {
        this.itens.add(item);
        System.out.println(item.getNome() + " adicionado ao inventário.");
    }

    public boolean removerItem(Item item) {
        return this.itens.remove(item);
    }

    public List<Item> getItens() {
        return itens;
    }

    // Lógica para usar um item. Agora ela está no Inventario, e não no BatalhaService.
    public void usarItem(Item item, Criatura criatura, int hpMaximo) {
        if (!this.itens.contains(item)) {
            System.out.println("O item " + item.getNome() + " não está no inventário.");
            return;
        }

        if ("Cura".equals(item.getTipo())) {
            int novoHp = criatura.getHp() + item.getValorEfeito();
            criatura.setHp(Math.min(novoHp, hpMaximo));
            System.out.println(criatura.getNome() + " usou " + item.getNome() + " e curou " + item.getValorEfeito() + " HP!");
        } else if ("Ataque".equals(item.getTipo())) {
            int novoAtk = criatura.getAtk() + item.getValorEfeito();
            criatura.setAtk(novoAtk);
            System.out.println(criatura.getNome() + " usou " + item.getNome() + " e seu ataque aumentou em " + item.getValorEfeito() + "!");
        }

        // Remove o item do inventário após o uso
        this.removerItem(item);
    }
}

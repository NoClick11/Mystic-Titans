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

    public void usarItem(Item item, Criatura criatura) {
        if (!this.itens.contains(item)) {
            System.out.println("O item " + item.getNome() + " não está no inventário.");
            return;
        }
        item.usar(criatura);
        this.removerItem(item);
    }
}

package model;

import java.util.ArrayList;
import java.util.List;

public class Criatura {
    private String nome;
    private int hp;
    private int atk;
    private int def;
    private int velocidade;
    private int mana;
    private int manaMaxima;
    private TipoElemental tipo;
    private List<EfeitoStatus> efeitos;
    private Inventario inventario;
    private boolean podeAgir;
    private List<Habilidade> habilidades;

    public Criatura () {
    }

    public Criatura(String nome, int hp, int atk, int def,int velocidade ,TipoElemental tipo) {
        this.nome = nome;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.velocidade = velocidade;
        this.tipo = tipo;
        this.efeitos = new ArrayList<>();
        this.inventario = new Inventario();
        this.podeAgir = true;
    }

    public Criatura(String nome, int hp, int atk, int def, int velocidade, int mana, int manaMaxima, TipoElemental tipo, List<Habilidade> habilidades) {
        this.nome = nome;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.velocidade = velocidade;
        this.mana = mana;
        this.manaMaxima = manaMaxima;
        this.tipo = tipo;
        this.efeitos = new ArrayList<>();
        this.inventario = new Inventario();
        this.podeAgir = true;
        this.habilidades = habilidades;
    }

    public String getNome() {
        return nome;
    }

    public int getHp() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getManaMaxima() {
        return manaMaxima;
    }

    public TipoElemental getTipo() {
        return tipo;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public List<EfeitoStatus> getEfeitos() {
        return efeitos;
    }

    public void aplicarEfeito(EfeitoStatus efeito) {
        this.efeitos.add(efeito);
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public boolean podeAgir() {
        return podeAgir;
    }

    public void setPodeAgir(boolean podeAgir) {
        this.podeAgir = podeAgir;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public List<Habilidade> getHabilidades() {
        return habilidades;
    }

    public void adicionarHabilidade(Habilidade habilidade) {
        this.habilidades.add(habilidade);
    }
}

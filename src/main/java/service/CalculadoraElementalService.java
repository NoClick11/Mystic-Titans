package service;

import model.TipoElemental;

import java.util.EnumMap;
import java.util.Map;

public class CalculadoraElementalService {

    private static final Map<TipoElemental, TipoElemental> VANTAGENS = new EnumMap<>(TipoElemental.class);
    private static final Map<TipoElemental, TipoElemental> DESVANTAGENS = new EnumMap<>(TipoElemental.class);

    static {
        VANTAGENS.put(TipoElemental.FOGO, TipoElemental.TERRA);
        VANTAGENS.put(TipoElemental.AGUA, TipoElemental.FOGO);
        VANTAGENS.put(TipoElemental.TERRA, TipoElemental.AGUA);
        VANTAGENS.put(TipoElemental.AR, TipoElemental.LUZ);
        VANTAGENS.put(TipoElemental.LUZ, TipoElemental.TREVAS);
        VANTAGENS.put(TipoElemental.TREVAS, TipoElemental.AR);

        DESVANTAGENS.put(TipoElemental.TERRA, TipoElemental.FOGO);
        DESVANTAGENS.put(TipoElemental.FOGO, TipoElemental.AGUA);
        DESVANTAGENS.put(TipoElemental.AGUA, TipoElemental.TERRA);
        DESVANTAGENS.put(TipoElemental.LUZ, TipoElemental.AR);
        DESVANTAGENS.put(TipoElemental.TREVAS, TipoElemental.LUZ);
        DESVANTAGENS.put(TipoElemental.AR, TipoElemental.TREVAS);
    }

    public double calcularDano(int atk, int def, TipoElemental tipoAgressor, TipoElemental tipoAlvo) {
        double multiplicador = 1.0;

        if (VANTAGENS.get(tipoAgressor) == tipoAlvo) {
            multiplicador = 2.0;
            System.out.println("É super eficaz!");
        } else if (DESVANTAGENS.get(tipoAgressor) == tipoAlvo) {
            multiplicador = 0.5;
            System.out.println("Não é muito eficaz...");
        }

        double danoBase = Math.max(0, atk - def);
        return danoBase * multiplicador;
    }
}


package org.example.ruleta;

import org.example.modelo.Individuo;

public class CruceUnPunto {

//    public static Individuo cruce1Punto(Individuo padre1, Individuo padre2, int bitDeCorte) {
//
//        String sub1 = padre1.getBinario().substring(bitDeCorte - 1, padre1.getBinario().length());
//        String sub2 = padre2.getBinario().substring(bitDeCorte - 1, padre2.getBinario().length());
//
////        System.out.println(sub2);
//        String bin1 = padre1.getBinario().substring(0, padre1.getBinario().length() - bitDeCorte) + sub2;
//        String bin2 = padre2.getBinario().substring(0, padre2.getBinario().length() - bitDeCorte) + sub1;

    /// /        double adaptado1 = SeleccionRuleta.realAadaptado(SeleccionRuleta.decimalAreal(SeleccionRuleta.binarioAdecimal(bin1), 2, 12, 13));
    /// /        double adaptado2 = SeleccionRuleta.realAadaptado(SeleccionRuleta.decimalAreal(SeleccionRuleta.binarioAdecimal(bin2), 2, 12, 13));
//        double adaptado1 = SeleccionRuleta.adaptarBinario(bin1);
//        double adaptado2 = SeleccionRuleta.adaptarBinario(bin2);
//        Individuo hijo1 = new Individuo(bin1, adaptado1);
//        Individuo hijo2 = new Individuo(bin2, adaptado2);
//
//        Individuo mejor = (hijo1.getAdaptado() > hijo2.getAdaptado()) ? hijo1 : hijo2;
//
//
//        return mejor;
//    }
    public static Individuo cruce1Punto(Individuo padre1, Individuo padre2, int bitDeCorte) {
        // Validación de parámetros
        if (padre1 == null || padre2 == null) {
            throw new IllegalArgumentException("Los padres no pueden ser null");
        }
        System.out.println("---------------------------------------------------------------------------");

//        System.out.println("padre 1 : " + padre1.toString());
//        System.out.println("padre 2 : " + padre2.toString());

        String binario1 = padre1.getBinario();
        String binario2 = padre2.getBinario();
        int longitud = binario1.length();

        // Validar que las cadenas tengan la misma longitud
        if (longitud != binario2.length()) {
            throw new IllegalArgumentException("Los cromosomas deben tener la misma longitud");
        }

        // Validar bit de corte (entre 1 y longitud-1)
        if (bitDeCorte < 1 || bitDeCorte >= longitud) {
            throw new IllegalArgumentException("El bit de corte debe estar entre 1 y " + (longitud - 1));
        }

        // Obtener subcadenas desde el bit de corte (posición bitDeCorte, contando desde 1)
        String sub1 = binario1.substring(longitud - bitDeCorte);
        String sub2 = binario2.substring(longitud - bitDeCorte);


        // Parte izquierda (bits antes del corte)
        String izq1 = binario1.substring(0, longitud - bitDeCorte);
        String izq2 = binario2.substring(0, longitud - bitDeCorte);

        // Crear nuevos individuos con cruce de un punto (intercambiando las partes derechas)
        String binHijo1 = izq1 + sub2;
        String binHijo2 = izq2 + sub1;

        // Calcular adaptación
        double adaptado1 = SeleccionRuleta.adaptarBinario(binHijo1);

        double adaptado2 = SeleccionRuleta.adaptarBinario(binHijo2);

        // Crear hijos
        Individuo hijo1 = new Individuo(binHijo1, adaptado1);
//        System.out.println("hijo-1 :" + hijo1.toString());
        Individuo hijo2 = new Individuo(binHijo2, adaptado2);
//        System.out.println("hijo-2 : " + hijo2.toString());

        System.out.println("---------------------------------------------------------------------------");


        // Retornar el mejor hijo
        return (hijo1.getAdaptado() > hijo2.getAdaptado()) ? hijo1 : hijo2;
    }


    public static Individuo cruceDosPuntos(Individuo padre1, Individuo padre2, int r1, int r2) {

        String hijo1 = obtenerRango(padre1.getBinario(), padre2.getBinario(), r1, r2);
        String hijo2 = obtenerRango(padre2.getBinario(), padre1.getBinario(), r1, r2);

        double adaptared1 = SeleccionRuleta.adaptarBinario(hijo1);
        double adaptared2 = SeleccionRuleta.adaptarBinario(hijo2);

        Individuo individuo1 = new Individuo(hijo1, adaptared1);
        Individuo individuo2 = new Individuo(hijo2, adaptared2);
        return (individuo1.getAdaptado() > individuo2.getAdaptado()) ? individuo1 : individuo2;
    }

    private static String obtenerRango(String binario, String padre2, int r1, int r2) {
        StringBuilder hijo = new StringBuilder();
        for (int i = binario.length() - 1, j = 0; i >= 0; i--, j++) {
            if (j >= r1 - 1 && j <= r2 - 1) {
                hijo.append(binario.charAt(i));
            } else hijo.append(padre2.charAt(i));
        }

        return hijo.toString();
    }


}

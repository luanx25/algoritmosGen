package org.example.ruleta;

import org.example.modelo.Individuo;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SeleccionRuleta {
    public static void main(String[] args) {

        List<String> poblacion = List.of(
                "1011010010110",
                "0100111101001",
                "1110001011010",
                "0011100110101",
                "1001011100011",
                "0110101011110",
                "1101000100111",
                "0001111011001",
                "1010110011100",
                "0101001110001",
                "1111010001010",
                "0010011110110",
                "1001100101011",
                "0111110001101",
                "1100011010100"
        );
//        List<String> poblacion = List.of(
//                "0110100101010",
//                "1001011001010",
//                "0101010010100",
//                "1010100101001",
//                "0101010010101",
//                "0001001001001",
//                "1000100000100",
//                "0100010000100",
//                "0100000100010",
//                "0001000111111"
//        );
//        List<String> poblacion = List.of(
//                "0110100101010",
//                "0110100101001",
//                "0110100101001",
//                "0110100100100",
//                "1001011000010",
//                "1001011101111",
//                "0101010010101",
//                "0101010010100",
//                "0101010010010",
//                "0100010000100"
//        );
        Scanner sc = new Scanner(System.in);

        int longitud = poblacion.get(0).length();
        System.out.println("introducir el minimo");
        int minimo = sc.nextInt();
        System.out.println("introducir el maximo");
        int maximo = sc.nextInt();

        System.out.println("paso 1 convertir poblacion de binario a adaptado");
        System.out.println("poblacion inicial");
        poblacion.forEach(System.out::println);


        ArrayList<Individuo> individuos = new ArrayList<>();
        Map<String, Double> mapa = new HashMap<>();
        for (String individuo : poblacion) {
            int dec = binarioAdecimal(individuo);
            double real = decimalAreal(dec, minimo, maximo, longitud);
            double adaptado = realAadaptado(real);
            mapa.put(individuo, adaptado);
            individuos.add(new Individuo(individuo, adaptado));

        }
        System.out.println("paso 2 individuos adaptados");

        individuos.forEach(System.out::println);

        System.out.println("paso 3 seleccion ruleta");
        individuos.sort(Individuo::compareTo);
        individuos.forEach(System.out::println);

        System.out.println("cruce 1 punto : 1");
        System.out.println("cruce 2 puntos : 2");
        int cruce = sc.nextInt();
        int bitCorte = 0;
        int bit1 = 0;
        int bit2 = 0;
        if (cruce == 1) {
            System.out.println("bit de corte");
            bitCorte = sc.nextInt();
        } else if (cruce == 2) {
            System.out.println("punto 1: ");
            bit1 = sc.nextInt();
            System.out.println("punto 2: ");
            bit2 = sc.nextInt();

        }


        System.out.println("numero de cruces");
        int cruces = sc.nextInt();

        int container = 0;
        int generacion = 1;

        double limInf = realAadaptado((double) minimo);
        double limSup = realAadaptado((double) maximo);
        System.out.println("limite superior : " + limSup);

        while (container < 9) {

            for (int i = 0; i < cruces; i++) {
                Individuo padre1 = individuos.get(generarRandom(poblacion));
                Individuo padre2 = individuos.get(generarRandom(poblacion));

                //cruce 1 punto
                if (cruce == 1) {
                    Individuo mejorHijo = CruceUnPunto.cruce1Punto(padre1, padre2, bitCorte);
                    if (padre1.getAdaptado() >= padre2.getAdaptado()) {
                        padre2.setBinario(mejorHijo.getBinario());
                        padre2.setAdaptado(mejorHijo.getAdaptado());
                    } else {
                        padre1.setBinario(mejorHijo.getBinario());
                        padre1.setAdaptado(mejorHijo.getAdaptado());
                    }

                    //cruce 2 puntos
                } else if (cruce == 2) {
                    Individuo mejorHijo = CruceUnPunto.cruceDosPuntos(padre1, padre2, bit1, bit2);
                    if (padre1.getAdaptado() >= padre2.getAdaptado()) {
                        padre2.setBinario(mejorHijo.getBinario());
                        padre2.setAdaptado(mejorHijo.getAdaptado());
                    } else {
                        padre1.setBinario(mejorHijo.getBinario());
                        padre1.setAdaptado(mejorHijo.getAdaptado());
                    }
                }

            }
            mutacionSimple(individuos.get(generarRandom(poblacion)), bitRandom(poblacion.get(0).length()));
            System.out.println("generacion: " + generacion);
            individuos.sort(Individuo::compareTo);
            individuos.forEach(System.out::println);
            generacion++;

            if (individuos.get(0).getAdaptado() >= limSup) {
                individuos.set(0, crearIndividuo());
                container++;
            }


        }


//        List<Integer> padre1 = List.of(5, 8, 3, 4, 13, 8, 2, 14, 13, 2, 11);
//        List<Integer> padre2 = List.of(13, 7, 9, 12, 2, 11, 9, 2, 13, 11, 15);

//        List<Integer> padre1 = List.of(3, 6, 2, 8, 1, 9, 6);
//        List<Integer> padre2 = List.of(8, 5, 1, 6, 10, 3, 2);

//        List<Integer> padre1 = List.of(2, 8, 3, 4, 1, 8, 6);
//        List<Integer> padre2 = List.of(6, 9, 5, 9, 6, 10, 4);


//        ArrayList<Individuo> mejoresIndividuos = new ArrayList<>();

//        List<Individuo> individuos2 = copyList(individuos);
//
//        for (int j = 0; j < 1; j++) {
//            for (int i = 0; i < padre1.size(); i++) {
//                Individuo p1 = individuos.get(padre1.get(i) - 1);
//                Individuo p2 = individuos.get(padre2.get(i) - 1);
//                System.out.println("cruce :" +i);
//                Individuo cruced = CruceUnPunto.cruce1Punto(p1, p2, 4);
//                if (p2.getAdaptado() > p1.getAdaptado()) {
////                    individuos.set(padre1.get(i) - 1, cruced);
//                    p1.setBinario(cruced.getBinario());
//                    p1.setAdaptado(cruced.getAdaptado());
//
//                } else {
//                    p2.setBinario(cruced.getBinario());
//                    p2.setAdaptado(cruced.getAdaptado());
//                }
//                mutacionSimple(individuos.get(1), 6);
//            }
//            individuos.sort(Individuo::compareTo);
//
//            if (individuos.get(0).getAdaptado() >= limSup) {
//                mejoresIndividuos.add(individuos.get(0));
//                individuos.remove(0);
//                Individuo crearIndividuo = crearIndividuo();
//                System.out.println("individuo generado : " + crearIndividuo);
//                individuos.add(crearIndividuo);
//            }

//            System.out.println("paso 4 cruce 1 punto generacion : " + j);
//            individuos.forEach(System.out::println);
//        }
//
//        for (int i = 0; i < padre1.size(); i++) {
//            Individuo padre_1 = individuos2.get(padre1.get(i) - 1);
//            Individuo padre_2 = individuos2.get(padre2.get(i) - 1);
//            Individuo mejorHijo = CruceUnPunto.cruceDosPuntos(padre_1, padre_2, 2, 7);
//            if (mejorHijo.getAdaptado() > padre_1.getAdaptado()) {
//                padre_1.setBinario(mejorHijo.getBinario());
//                padre_1.setAdaptado(mejorHijo.getAdaptado());
//            } else {
//                padre_2.setBinario(mejorHijo.getBinario());
//                padre_2.setAdaptado(mejorHijo.getAdaptado());
//            }
//        }
//
//        individuos2.sort(Individuo::compareTo);
//        System.out.println("paso 5 cruce 2 puntos:  ");
//        individuos2.forEach(System.out::println);


//        System.out.println("\n=== MOSTRANDO GRÁFICA DE LÍMITES ===");
//        GraficaLimites.mostrarGraficaConLimites(individuos, limInf, limSup);


    }

    private static int generarRandom(List<String> poblacion) {
//        return (int) Math.random() * poblacion.size();
        return ThreadLocalRandom.current().nextInt(0, poblacion.size());
    }


    private static int bitRandom(int rango) {
//        return (int) Math.random() * rango;
        return ThreadLocalRandom.current().nextInt(0, rango);
    }

//    private static List<Individuo> copyList(ArrayList<Individuo> individuos) {
//        ArrayList<Individuo> individuos2 = new ArrayList<>();
//        for (Individuo individuo : individuos) {
//            individuos2.add(new Individuo(individuo.getBinario(), individuo.getAdaptado()));
//        }
//        return individuos2;
//    }

    private static void mutacionSimple(Individuo individuo, int bitDeReferencia) {
        StringBuilder nuevoBinario = new StringBuilder();
        String binario = individuo.getBinario();
        for (int i = 0; i < binario.length(); i++) {
            if (i == bitDeReferencia - 1) {
                char ms = (binario.charAt(i) == '1') ? '0' : '1';
                nuevoBinario.append(ms);

            } else nuevoBinario.append(binario.charAt(i));
        }
        double adaptado = adaptarBinario(nuevoBinario.toString());
        individuo.setAdaptado(adaptado);
        individuo.setBinario(nuevoBinario.toString());

    }


    public static double adaptarBinario(String binario) {

        return realAadaptado(decimalAreal(binarioAdecimal(binario), 2, 12, 13));

    }


    private static Individuo crearIndividuo() {
        StringBuilder binario = new StringBuilder();

        for (int i = 0; i < 13; i++) {
            Random rand = new Random();
            int nextInt = rand.nextInt(2);
            binario.append(nextInt);
        }
        double adaptado = adaptarBinario(binario.toString());
        return new Individuo(binario.toString(), adaptado);
    }


    public static int binarioAdecimal(String binario) {

        int decimal = 0;
        int exp = 0;
        for (int i = binario.length() - 1; i >= 0; i--) {
            char c = binario.charAt(i);
            if (c == '1') {
                int entero = Integer.parseInt(String.valueOf(c));
                decimal += Math.pow(2, exp);
            }
            exp++;
        }
//        System.out.println("decimal : " + decimal);
        return decimal;

    }

    public static double decimalAreal(int decimal, int minimo, int maximo, int longitud) {
        int numerador = maximo - minimo;
        double denominador = Math.pow(2, longitud);
        double resultado = decimal * numerador / (denominador - 1);

        double res = resultado + minimo;

//        System.out.println("real : " + res);

        return res;
    }

    public static double realAadaptado(Double real) {
//        double resultado = (5 * Math.sin(real)) + (2 * real) - 5;
        double resultado = 2 * real * Math.cos(real) + 3 * real + 1;

//        System.out.println("adaptado : " + resultado);


        return resultado;

    }


}

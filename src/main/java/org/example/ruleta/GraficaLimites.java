package org.example.ruleta;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.example.modelo.Individuo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraficaLimites {

    public static void mostrarGraficaConLimites(List<Individuo> individuos, double limInf, double limSup) {
        // Crear dataset
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Serie para los individuos
        XYSeries serieIndividuos = new XYSeries("Individuos");

        // Ordenar individuos por adaptación
        individuos.sort(Individuo::compareTo);

        // Agregar cada individuo a la serie (posición en el ranking, adaptación)
        for (int i = 0; i < individuos.size(); i++) {
            Individuo ind = individuos.get(i);
            serieIndividuos.add(i + 1, ind.getAdaptado());
        }
        dataset.addSeries(serieIndividuos);

        // Serie para el límite superior
        XYSeries serieLimSup = new XYSeries("Límite Superior");
        serieLimSup.add(0, limSup);
        serieLimSup.add(individuos.size() + 1, limSup);
        dataset.addSeries(serieLimSup);

        // Serie para el límite inferior
        XYSeries serieLimInf = new XYSeries("Límite Inferior");
        serieLimInf.add(0, limInf);
        serieLimInf.add(individuos.size() + 1, limInf);
        dataset.addSeries(serieLimInf);

        // Serie para la media
        double media = individuos.stream()
                .mapToDouble(Individuo::getAdaptado)
                .average()
                .orElse(0);
        XYSeries serieMedia = new XYSeries("Media");
        serieMedia.add(0, media);
        serieMedia.add(individuos.size() + 1, media);
        dataset.addSeries(serieMedia);

        // Crear gráfica
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Límites de Adaptación y Posición de Individuos",
                "Posición en Ranking",
                "Valor de Adaptación",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Personalizar el gráfico
        XYPlot plot = chart.getXYPlot();

        // Renderer personalizado para diferentes estilos
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // Individuos: puntos y línea
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, new Color(31, 97, 141)); // Azul oscuro
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        // Límite Superior: línea roja punteada
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, false);
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 1.0f, new float[]{10.0f, 5.0f}, 0.0f));

        // Límite Inferior: línea verde punteada
        renderer.setSeriesLinesVisible(2, true);
        renderer.setSeriesShapesVisible(2, false);
        renderer.setSeriesPaint(2, new Color(0, 128, 0)); // Verde
        renderer.setSeriesStroke(2, new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 1.0f, new float[]{10.0f, 5.0f}, 0.0f));

        // Media: línea naranja discontinua
        renderer.setSeriesLinesVisible(3, true);
        renderer.setSeriesShapesVisible(3, false);
        renderer.setSeriesPaint(3, Color.ORANGE);
        renderer.setSeriesStroke(3, new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 1.0f, new float[]{5.0f, 3.0f}, 0.0f));

        plot.setRenderer(renderer);

        // Mejorar el fondo y la cuadrícula
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);

        // Crear panel y ventana
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(2000, 1200));

        JFrame frame = new JFrame("Gráfica de Límites - Selección por Ruleta");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Imprimir información en consola
        imprimirInformacion(individuos, limInf, limSup, media);
    }

    private static void imprimirInformacion(List<Individuo> individuos, double limInf, double limSup, double media) {
        System.out.println("\n=== INFORMACIÓN DE LA GRÁFICA ===");
        System.out.printf("Límite Inferior: %.4f%n", limInf);
        System.out.printf("Límite Superior: %.4f%n", limSup);
        System.out.printf("Media: %.4f%n", media);
        System.out.printf("Rango: %.4f%n", limSup - limInf);

        // Contar individuos por categoría
        long sobreLimSup = individuos.stream()
                .filter(i -> i.getAdaptado() >= limSup)
                .count();
        long entreLimites = individuos.stream()
                .filter(i -> i.getAdaptado() >= limInf && i.getAdaptado() < limSup)
                .count();
        long bajoLimInf = individuos.stream()
                .filter(i -> i.getAdaptado() < limInf)
                .count();

        System.out.println("\nDistribución de individuos:");
        System.out.printf("✓ Sobre el límite superior: %d (%.1f%%)%n",
                sobreLimSup, sobreLimSup * 100.0 / individuos.size());
        System.out.printf("✓ Entre límites: %d (%.1f%%)%n",
                entreLimites, entreLimites * 100.0 / individuos.size());
        System.out.printf("✓ Bajo el límite inferior: %d (%.1f%%)%n",
                bajoLimInf, bajoLimInf * 100.0 / individuos.size());

        // Mejor y peor individuo
        Individuo mejor = individuos.get(0);
        Individuo peor = individuos.get(individuos.size() - 1);
        System.out.println("\nMejor individuo:");
        System.out.printf("  Binario: %s%n", mejor.getBinario());
        System.out.printf("  Adaptación: %.4f%n", mejor.getAdaptado());
        System.out.println("Peor individuo:");
        System.out.printf("  Binario: %s%n", peor.getBinario());
        System.out.printf("  Adaptación: %.4f%n", peor.getAdaptado());
        System.out.printf("Diferencia mejor-peor: %.4f%n", mejor.getAdaptado() - peor.getAdaptado());
    }
}

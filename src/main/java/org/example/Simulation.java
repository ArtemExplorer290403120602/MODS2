package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Simulation {
    private static final int N = 1000;

    public static void main(String[] args) {
        double[] gran1 = {200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200};
        double[] gran2 = {0.2, 0.32, 0.44, 0.56, 0.68, 0.8, 0.92, 1.04, 1.16, 1.28, 1.4};

        int[] m1 = new int[gran1.length - 1];
        int[] m2 = new int[gran2.length - 1];

        simulateI(m1, gran1);
        simulateSearchTime(m2, gran2);

        showHistogram("Histogram of I", createDataset(m1, gran1), "Intervals", "Frequency");
        showHistogram("Histogram of search time", createDataset(m2, gran2), "Intervals", "Frequency");
    }

    private static void simulateI(int[] m, double[] gran) {
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            double x = simulateI(rand);
            for (int j = 0; j < gran.length - 1; j++) {
                if (x > gran[j] && x <= gran[j + 1]) {
                    m[j]++;
                }
            }
        }
    }

    private static void simulateSearchTime(int[] m, double[] gran) {
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            double x = simulateSearchTime(rand);
            for (int j = 0; j < gran.length - 1; j++) {
                if (x > gran[j] && x <= gran[j + 1]) {
                    m[j]++;
                }
            }
        }
    }

    private static double simulateI(Random rand) {
        double U = rand.nextDouble();
        if (U <= 0.2) {
            return (U + 0.2) / 0.001;
        } else if (U <= 0.8) {
            return (U + 0.4) / 0.0015;
        } else {
            return (U - 0.4) / 0.0005;
        }
    }

    private static double simulateSearchTime(Random rand) {
        return 0.2 + (1.4 - 0.2) * rand.nextDouble();
    }

    private static CategoryDataset createDataset(int[] m, double[] gran) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < m.length; i++) {
            dataset.addValue((double)m[i] / N, "Frequency", gran[i] + " - " + gran[i + 1]);
        }
        return dataset;
    }

    private static void showHistogram(String title, CategoryDataset dataset, String xLabel, String yLabel) {
        JFreeChart barChart = ChartFactory.createBarChart(title, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
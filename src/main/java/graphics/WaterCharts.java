package graphics;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;

/**
 * Genera un JFrame con dos gráficas lineales:
 *  1. Water Balance (WB)
 *  2. Available Water (AW)
 * 
 * Basado en el modelo de Dinámica de Sistemas (Rafsanjan adaptado a Oaxaca)
 */
public class WaterCharts extends JFrame {

    public WaterCharts(double[] years, double[] waterBalance, double[] availableWater) {
        setTitle("Oaxaca Water Simulation Results");
        setLayout(new GridLayout(2, 1)); // Dos filas: WB arriba, AW abajo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 800);
        setLocationRelativeTo(null);

        // --- 2. GRAFICA: AVAILABLE WATER ---
        XYSeries seriesAW = new XYSeries("Available Water");
        for (int i = 0; i < years.length; i++) {
            seriesAW.add(years[i], availableWater[i]);
        }

        XYSeriesCollection datasetAW = new XYSeriesCollection(seriesAW);
        JFreeChart chartAW = ChartFactory.createXYLineChart(
                "Available Water (AW)",
                "Year",
                "Cubic meters",
                datasetAW,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel panelAW = new ChartPanel(chartAW);
        panelAW.setMouseWheelEnabled(false);
        
        // --- 1. GRAFICA: WATER BALANCE ---
        XYSeries seriesWB = new XYSeries("Water Balance");
        for (int i = 0; i < years.length; i++) {
            seriesWB.add(years[i], waterBalance[i]);
        }

        XYSeriesCollection datasetWB = new XYSeriesCollection(seriesWB);
        JFreeChart chartWB = ChartFactory.createXYLineChart(
                "Water Balance (WB)",
                "Year",
                "Cubic meters / year",
                datasetWB,
                PlotOrientation.VERTICAL,
                true,  // Leyenda
                true,  // Tooltips
                false  // URLs
        );

        ChartPanel panelWB = new ChartPanel(chartWB);
        panelWB.setMouseWheelEnabled(false);

        // --- AGREGAR LOS DOS PANELES AL MISMO FRAME ---
        add(panelWB);
        add(panelAW);
    }

    public static void showCharts(double[] years, double[] waterBalance, double[] availableWater) {
        SwingUtilities.invokeLater(() -> {
            WaterCharts frame = new WaterCharts(years, waterBalance, availableWater);
            frame.setVisible(true);
        });
    }
    
    public void stagebuttons(){
        JButton scenery1 = new JButton();
        scenery1.setText("Escenario 1");
        scenery1.setSize(200, 30);
        scenery1.setLocation(700, 700);
        add(scenery1);
    }
}

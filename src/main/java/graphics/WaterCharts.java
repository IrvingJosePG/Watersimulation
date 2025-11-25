package graphics;

import interpretations.Interpretacion1;
import interpretations.Interpretacion2;
import interpretations.Interpretacion3;
import interpretations.Interpretacion4;
import interpretations.Interpretacion5;
import interpretations.Interpretacionfinal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.*;
import utils.ShortNumberFormat;
/**
 * Genera un JFrame con dos gráficas lineales:
 *  1. Water Balance (WB)
 *  2. Available Water (AW)
 * Incluye un BOTÓN para regresar a la interfaz anterior.
 */

public class WaterCharts extends JFrame {
    
    private int escenario;

    public WaterCharts(double[] years,
                   double[] wbCurrent, double[] awCurrent,
                   double[] wbOptimal, double[] awOptimal,
                   String text,
                   int escenario) {

        this.escenario = escenario;

        setTitle("Escenario - Resultados de la simulación del agua de Oaxaca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 880);
        setLocationRelativeTo(null);

        // PANEL PRINCIPAL (BORDERLAYOUT) — arriba botón, centro gráficas
        JPanel mainPanel = new JPanel(new BorderLayout());

        // BARRA SUPERIOR CON BOTÓN
        //Panel Superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        
        //Boton Regresar
        JButton backButton = new JButton("⬅ Regresar");

        // Acción del botón
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainInterface mi = new MainInterface();
                mi.setVisible(true);
                dispose();
            }
        });
        //Boton Interpretacion
        JButton interpButton = new JButton("Interpretaciones ⬅");
        // Acción del botón
        interpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la interpretación correspondiente según escenarioActual
        switch (escenario) {
            case 1:
                new Interpretacion1().setVisible(true);
                break;
            case 2:
                new Interpretacion2().setVisible(true);
                break;
            case 3:
                new Interpretacion3().setVisible(true);
                break;
            case 4:
                new Interpretacion4().setVisible(true);
                break;
            case 5:
                new Interpretacion5().setVisible(true);
                break;
            case 6:
                new Interpretacionfinal().setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(null, "No hay escenario seleccionado.");
                return;
        }

        dispose();
            }
        });

        JLabel subtitle = new JLabel(
            "Las gráficas AZULES representan valores ACTUALES y las ROJAS representan valores ÓPTIMOS.",
            SwingConstants.CENTER
            );
        subtitle.setFont(new Font("PT Sans", Font.PLAIN, 13));
        subtitle.setForeground(Color.DARK_GRAY);

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(subtitle, BorderLayout.CENTER);
        topPanel.add(interpButton, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // PANEL CENTRAL CON GRÁFICAS
        JPanel chartsPanel = new JPanel(new GridLayout(2, 1));
        chartsPanel.add(createAvailableWaterChart(years, awCurrent, awOptimal, text));
        chartsPanel.add(createWaterBalanceChart(years, wbCurrent, wbOptimal, text));

        mainPanel.add(chartsPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    //   GRAFICA 1 – WATER BALANCE
    private ChartPanel createWaterBalanceChart(double[] years, double[] current,
                                               double[] optimal, String text) {

        // --- 1. Crear las series de datos ---
        XYSeries s1 = new XYSeries("Balance hídrico – Actual   "); // Serie azul: estado actual
        XYSeries s2 = new XYSeries("Balance hídrico – Óptimo   " + text); // Serie roja: estado óptimo 

        // Llenamos las dos series con los valores año por año
        for (int i = 0; i < years.length; i++) {
            s1.add(years[i], current[i]);
            s2.add(years[i], optimal[i]); // Agrega punto (x,y) para la curva
        }

        // --- 2. Agregar las series a un dataset ---
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1); // Curva azul
        dataset.addSeries(s2); // Curva roja
        
        // --- 3. Crear el gráfico con JFreeChart ---
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Balance hídrico (WB)",       // Título del gráfico
                "Año",                     // Etiqueta del eje X
                "m³/Año",                  // Etiqueta del eje Y
                dataset,                    // Conjunto de datos
                PlotOrientation.VERTICAL,   // Orientación vertical del gráfico
                true, true, false);         // Mostrar leyenda, Mostrar tooltips, No usar URLs

        // Obtenemos el "plot" donde están las curvas
        XYPlot plot = chart.getXYPlot();
        
        // --- 4. Personalizar eje X con formato 2020 ---
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setNumberFormatOverride(new java.text.DecimalFormat("0"));  


        // --- 5. Personalizar eje Y con formato 5M / 3B ---
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setNumberFormatOverride(new ShortNumberFormat());
        // ShortNumberFormat nos ayuda a convertir grandes números a: 1.2M, 3.5B, etc.
        
         // --- 6. Fondo blanco y estilo profesional ---
        chart.setBackgroundPaint(Color.WHITE);           // Fondo del contenedor del gráfico
        plot.setBackgroundPaint(Color.WHITE);            // Fondo del área donde están las curvas
        
        // Creamos espacio interno para que la gráficA quede centrada
        plot.setAxisOffset(new RectangleInsets(10, 10, 10, 10));

        // --- 7. Activar la cuadrícula ---
        plot.setDomainGridlinesVisible(true);            // Cuadrícula vertical
        plot.setRangeGridlinesVisible(true);             // Cuadrícula horizontal
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);   // Color de la cuadrícula vertical
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);    // Color de la cuadrícula horizontal

        // --- 8. Personalizar líneas ---
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        
        // ACTIVAR TOOLTIP FLOTANTE PARA MOSTRAR (X,Y)
        renderer.setDefaultToolTipGenerator(
            new StandardXYToolTipGenerator(
                "{0}: (Año={1}, Valor={2})",
                new java.text.DecimalFormat("0"),
                new java.text.DecimalFormat("#,##0.###")
            )
        );

        renderer.setSeriesPaint(0, Color.BLUE);  // Línea serie 1: azul
        renderer.setSeriesPaint(1, Color.RED);   // Línea serie 2: rojo
        
        renderer.setSeriesShapesVisible(0, false); // Oculta marcadores en la línea azul
        renderer.setSeriesShapesVisible(1, false); // Oculta marcadores en la línea roja

        renderer.setSeriesStroke(0, new BasicStroke(2.5f)); // Grosor de línea azul
        renderer.setSeriesStroke(1, new BasicStroke(2.5f)); // Grosor de línea roja

        plot.setRenderer(renderer); // Aplicamos el renderer al plot
        
        // --- 8. Crear panel del gráfico ---
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(false); // Permite hacer zoom con rueda del mouse

        return panel; // Lo regresamos al frame principal
    }
 
     //   GRAFICA 2 – AVAILABLE WATER
    private ChartPanel createAvailableWaterChart(double[] years, double[] current,
                                                 double[] optimal, String text) {

        XYSeries s1 = new XYSeries("Agua disponible – Actual   ");
        XYSeries s2 = new XYSeries("Agua disponible - Óptimo   " + text);

        for (int i = 0; i < years.length; i++) {
            s1.add(years[i], current[i]);
            s2.add(years[i], optimal[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Agua disponible (AW)",
                "Año",
                "m³",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        
        XYPlot plot = chart.getXYPlot();

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setNumberFormatOverride(new java.text.DecimalFormat("0"));  

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setNumberFormatOverride(new ShortNumberFormat());

        chart.setBackgroundPaint(Color.WHITE);           
        plot.setBackgroundPaint(Color.WHITE);            
        
        plot.setAxisOffset(new RectangleInsets(10, 10, 10, 10));

        plot.setDomainGridlinesVisible(true);         
        plot.setRangeGridlinesVisible(true);         
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);  
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY); 

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setDefaultToolTipGenerator(
            new StandardXYToolTipGenerator(
                "{0}: (Año={1}, Valor={2})",
                new java.text.DecimalFormat("0"),
                new java.text.DecimalFormat("#,##0.###")
            )
        );

        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.RED); 

        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);

        renderer.setSeriesStroke(0, new BasicStroke(2.5f));
        renderer.setSeriesStroke(1, new BasicStroke(2.5f));

        plot.setRenderer(renderer);

        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(false);  //zoom a la grafica

        return panel;
    }

    //   MÉTODO PARA MOSTRAR LOS GRÁFICOS
    public static void showCharts(
            double[] years, double[] wbCurrent, double[] awCurrent,
            double[] wbOptimal, double[] awOptimal, String text, int escenario) {

        SwingUtilities.invokeLater(() -> {
            WaterCharts frame = new WaterCharts(
                    years, wbCurrent, awCurrent,
                    wbOptimal, awOptimal, text, escenario );
            frame.setVisible(true);
        });
    }
}

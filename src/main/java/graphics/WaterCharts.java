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

        setTitle("Resultados de la simulación del agua de Oaxaca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 880);
        setLocationRelativeTo(null);

        // PANEL PRINCIPAL
        JPanel mainPanel = new JPanel(new BorderLayout());

        // ---------------- PANEL SUPERIOR ----------------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Botón regresar
        JButton backButton = new JButton("⬅ Regresar");
        backButton.addActionListener(e -> {
            MainInterface mi = new MainInterface();
            mi.setVisible(true);
            dispose();
        });

        // Botón Interpretaciones
        JButton interpButton = new JButton("Interpretaciones ⬅");
        interpButton.addActionListener(e -> {
            switch (escenario) {
                case 1 -> new Interpretacion1().setVisible(true);
                case 2 -> new Interpretacion2().setVisible(true);
                case 3 -> new Interpretacion3().setVisible(true);
                case 4 -> new Interpretacion4().setVisible(true);
                case 5 -> new Interpretacion5().setVisible(true);
                case 6 -> new Interpretacionfinal().setVisible(true);
                default -> JOptionPane.showMessageDialog(null, "No hay escenario seleccionado.");
            }
            dispose();
        });

        JLabel subtitle = new JLabel( "Resultado del escenario sobre la " + text, 
                                SwingConstants.CENTER );

        subtitle.setFont(new Font("PT Sans", Font.PLAIN, 13));
        subtitle.setForeground(Color.DARK_GRAY);

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(subtitle, BorderLayout.CENTER);
       // topPanel.add(interpButton, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ---------------- INTERPRETACIONES ----------------
        String interpAW = "";
        String interpWB = "";

        switch (escenario) {
            case 1 -> {
                interpAW = "Si se produce 10% mas de agua se aumenta el agua almacenada con el paso de los años.";
                interpWB = "El suministro de agua contra la demanda urbana mejora debido a mayor recarga de agua que consumo. Pero se sigue agotando con los años";
            }
            case 2 -> {
                interpAW = "Reducir el consumo no potable de las personas aumenta el agua disponible a traves de los años.";
                interpWB = "El suministro de agua mejora gracias a menor demanda por persona.";
            }
            case 3 -> {
                interpAW = "Menor consumo industrial ayuda a conservar el agua disponible pero en menor efecto.";
                interpWB = "No se tiene un efecto considerable, se sigue reflejando una menor demanda industrial.";
            }
            case 4 -> {
                interpAW = "Las disminucion de natalidad modifica lentamente la disponibilidad del agua.";
                interpWB = "El suministro de agua contra la demanada de agua se mantiene casi igual y se sigue agotando con los años.";
            }
            case 5 -> {
                interpAW = "No se observan cambios en el agua disponible, se sigue almacenando el agua.";
                interpWB = "El suministro de agua se agota mas rapido a traves de los años.";
            }
            case 6 -> {
                interpAW = "Con todas las variables optimizadas, se observa agua disponible sin sufir una crisis de agua.";
                interpWB = "El suministro de agua domina sobre la demanda de agua al aplicar todas las mejoras.";
            }
        }

        // Definiciones FIJAS para todas las gráficas
        String defAW = "Cantidad de agua almacenada en los próximos años, considerando cuánta agua entra.";
        String defWB = "Muestra si entra más agua de la que se usa. Si entra más es positivo. Si se usa más es negativo.";

        // ---------------- PANEL CENTRAL ----------------
        JPanel chartsPanel = new JPanel(new GridLayout(2, 1));

        // GRAFICAS
        ChartPanel awChart = createAvailableWaterChart(years, awCurrent, awOptimal, text);
        ChartPanel wbChart = createWaterBalanceChart(years, wbCurrent, wbOptimal, text);

        // ENCAPSULAR CADA GRÁFICA CON SU TEXTO + DEFINICIÓN
        chartsPanel.add(wrapChartWithInterpretation(awChart, interpAW, defAW));
        chartsPanel.add(wrapChartWithInterpretation(wbChart, interpWB, defWB));

        mainPanel.add(chartsPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    // ---------------- MÉTODO: Envolver gráfica + interpretación + definición ----------------
    private JPanel wrapChartWithInterpretation(ChartPanel chart, String interpretation, String definition) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);

        chart.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel horizontal para colocar interpretación + definición
        JPanel textRow = new JPanel(new GridLayout(1, 2));
        textRow.setBackground(Color.WHITE);

        JLabel interpLabel = new JLabel(
                "<html><div style='text-align:center; padding:5px;'>" +
                interpretation +
                "</div></html>"
        );
        interpLabel.setFont(new Font("PT Sans", Font.PLAIN, 12));
        interpLabel.setForeground(Color.DARK_GRAY);

        JLabel defLabel = new JLabel(
                "<html><div style='text-align:center; padding:5px;'><b>Definición:</b> " +
                definition +
                "</div></html>"
        );
        defLabel.setFont(new Font("PT Sans", Font.PLAIN, 12));
        defLabel.setForeground(new Color(60, 60, 60));

        // Añadir ambos textos en la misma fila
        textRow.add(interpLabel);
        textRow.add(defLabel);

        // Agregar al panel main debajo de la gráfica
        p.add(chart);
        p.add(Box.createVerticalStrut(6));
        p.add(textRow);

        return p;
    }



    //   GRAFICA 1 – WATER BALANCE
    private ChartPanel createWaterBalanceChart(double[] years, double[] current,
                                               double[] optimal, String text) {

        // --- 1. Crear las series de datos ---
        XYSeries s1 = new XYSeries("La AZUL muestra la proyeccion sin aplicar mejoras   "); // Serie azul: estado actual
        XYSeries s2 = new XYSeries("La ROJA muestra la proyeccion con mejoras   " + text); // Serie roja: estado óptimo 

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

        XYSeries s1 = new XYSeries("La AZUL muestra la proyeccion sin aplicar mejoras   ");
        XYSeries s2 = new XYSeries("La ROJA muestra la proyeccion con mejoras   " + text);

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

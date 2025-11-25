package scenarios;

import graphics.WaterCharts;
import model.Integrator;
import model.OptimalParameters;
import model.Parameters;

public class Scenary1 {

    public void run() {

        int start = Parameters.currentyear;
        int N = Parameters.simulationduration;

        double[] years = new double[N];
        double[] awCurrent = new double[N];
        double[] awOptimal = new double[N];
        double[] wbCurrent = new double[N];
        double[] wbOptimal = new double[N];
        String text = " Variable: PR";

        // Integradores separados para el valor actual y optimo
        Integrator Icurrent = new Integrator();
        Integrator Ioptimal = new Integrator();

        for (int i = 0; i < N; i++) {
            years[i] = start + i;

            // ESCENARIO ACTUAL
            wbCurrent[i] = Icurrent.waterBalanceDynamic(Parameters.PR, Parameters.NWCP,
                                                    Parameters.ISWCP,Parameters.BR, Parameters.DR, Parameters.NSR);
            awCurrent[i] = Icurrent.updateAvailableWater();

            // ESCENARIO 1: PR óptimo ( +10% Rafsanjan adaptado a Oaxaca) 
            wbOptimal[i] = Ioptimal.waterBalanceDynamic(OptimalParameters.PR_Optimal , Parameters.NWCP,
                                                    Parameters.ISWCP,Parameters.BR, Parameters.DR, Parameters.NSR);
            awOptimal[i] = Ioptimal.updateAvailableWater();
        }

        // Mostrar gráfica
        WaterCharts.showCharts(years, wbCurrent, awCurrent, wbOptimal, awOptimal, text, 1);
    }
}

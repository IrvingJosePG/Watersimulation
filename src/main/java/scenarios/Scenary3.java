package scenarios;

import graphics.WaterCharts;
import model.Integrator;
import model.OptimalParameters;
import model.Parameters;

public class Scenary3 {

    public void run() {
        
        int start = Parameters.currentyear;
        int N = Parameters.simulationduration;

        double[] years = new double[N];
        double[] awCurrent = new double[N];
        double[] awOptimal = new double[N];
        double[] wbCurrent = new double[N];
        double[] wbOptimal = new double[N];
        String text = " Variable: ISWCP";

        
        Integrator Icurrent = new Integrator();
        Integrator Ioptimal = new Integrator();

        for (int i = 0; i < N; i++) {
            years[i] = start + i;

            // ESCENARIO ACTUAL
            wbCurrent[i] = Icurrent.waterBalanceDynamic(Parameters.PR, Parameters.NWCP,
                    Parameters.ISWCP, Parameters.BR, Parameters.DR, Parameters.NSR);
            awCurrent[i] = Icurrent.updateAvailableWater();

            // ESCENARIO 3: ISWCP óptimo
            wbOptimal[i] = Ioptimal.waterBalanceDynamic(Parameters.PR, Parameters.NWCP,
                    OptimalParameters.ISWCP_Optimal , Parameters.BR, Parameters.DR, Parameters.NSR);
            awOptimal[i] = Ioptimal.updateAvailableWater();
        }

        
        WaterCharts.showCharts(years, wbCurrent, awCurrent, wbOptimal, awOptimal, text, 3);
    }
}
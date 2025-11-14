package model;

    /*
     * Esta clase contiene todas las ecuaciones del modelo de Dinámica de Sistemas
       para la simulación del consumo y suministro de agua.
    
     * Aquí se implementan:
     *  - Cálculo de la demanda total de agua (WD)
     *  - Cálculo de la oferta total de agua (WS)
    
     * Cada ecuación sigue el modelo descrito en el artículo original.
     */

public class Equations{
    /* Calcula la demanda total de agua para un año dado.
       Ecuación general: WD = DU + NDW + ANU + AISU + ISW
    */
    public static double totalWaterDemand(double pop, double iss) {
        double DU = Parameters.ADWCP * pop;
        double NDW = Parameters.NWCP * pop * Parameters.FRACTION_NDW;
        double ANU = Parameters.NWCP * pop * Parameters.FRACTION_ANU;
        double AISU = iss * Parameters.ISWCP * Parameters.FRAC_IS_REAL;
        double ISW = iss * Parameters.ISWCP * Parameters.FRAC_IS_WASTE;
        
        double WD = ANU + AISU + DU + NDW + ISW;
        return WD;
    }

    /* Calcula la oferta total de agua disponible para el sistema.
       Ecuación del artículo:       WS = PR + ATR
    
       PR  = Precipitación anual recargada al acuífero
       ATR = Tasa anual de transferencia artificial de agua (inyección artificial)
     */
    public static double totalWatersupply(double PR, double ATR){
        double WS = PR + ATR;
        return WS;
    }
}

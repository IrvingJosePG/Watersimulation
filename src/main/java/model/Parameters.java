package model;

    /*
     * Esta clase contiene todas las parametros del modelo de Dinámica de Sistemas
       para la simulación del consumo y suministro de agua.
     * Aquí se implementan cada variable descrita en el articulo (excluyendo sector agricola)
       con sus respectivos valores
     */

public class Parameters { 
    public static final int currentyear = 2020;
    public static final int simulationduration = 100;

    // Paso de integración
    public static final double DELTA_T = 1.0;

    // Stocks iniciales
    public static final double POP_INITIAL = 270955;
    public static final double ISS_INITIAL = 26739;
    public static final double AW_INITIAL = 285770000;

    // Suministro de agua
    public static final double PR = 153600000;  // Recarga total anual
    public static final double ATR = 0;         // Transferencia artificial anual (no existe en Oaxaca)

    // Crecimiento de suscriptores (ISS)
    public static final double NSR = 749;       // Nuevos suscriptores al año

    // Parámetros de consumo individual
    public static final double ADWCP = 1.095;   // Agua potable per cápita
    public static final double NWCP = 138.53;   // Agua no potable per cápita
    public static final double ISWCP = 429.36;  // Consumo industrial/servicios por suscriptor

    // Fracciones del uso industrial
    public static final double FRAC_IS_REAL = 0.8;   // Uso real
    public static final double FRAC_IS_WASTE = 0.2;  // Pérdidas

    // Tasa de natalidad y mortalidad
    public static final double BR = 1924;
    public static final double DR = 4744;

    // Factores NDW / ANU del artículo
    public static final double FRACTION_NDW = 0.3;  // 30% del agua no potable → aguas residuales retornables
    public static final double FRACTION_ANU = 0.7;  // 70% del agua no potable → pérdidas no retornables
}


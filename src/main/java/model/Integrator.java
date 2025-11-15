package model;

    /* La clase Integrator ejecuta las integraciones del modelo de dinámica de sistemas.
       Aquí se calculan los stocks que cambian año con año:
        - Población (POP)
        - Número de suscriptores industriales y de servicios (ISS)
        - Agua disponible (AW)
        - Balance hídrico (WB) = (oferta WS) – (demanda WD)
        
        El integrador utiliza integración discreta tipo Euler:
            Stock(t) = Stock(t-1) + Flujo * Δt   ,  Δt = 1 año en este modelo.
    */

public class Integrator{
     // --- Stocks iniciales ---
    private double POP = Parameters.POP_INITIAL;
    private double ISS = Parameters.ISS_INITIAL;
    private double AW  = Parameters.AW_INITIAL;
    private double WB = 0;   // Último balance hídrico calculado
    private final double DELTA_T = Parameters.DELTA_T;  // Delta t = 1 año
    
    
    /* Calcula y retorna el balance hídrico del año, y ANTES actualiza todos los stocks
       que afectan el balance.
     
     * Fórmulas:
     *  1. WB = Water Supply - Water Demand */
    public double waterBalanceDynamic(double PR, double NWCP, double ISWCP,
                                      double BR, double DR, double NSR) {
        // --- 1. Actualizar los stocks (POP e ISS) ---
        updateStocks(BR, DR, NSR);

        // --- 2. Calcular demanda en función de parámetros dinámicos ---
        double WD = Equations.totalWaterDemandDynamic(POP, ISS, NWCP, ISWCP);

        // --- 3. Oferta (PR + ATR) ---
        double WS = Equations.totalWatersupply(PR, Parameters.ATR);

        // --- 4. Balance hídrico ---
        this.WB = WS - WD; // WB = WS - WD

        return this.WB;
    }   
    
    /* Actualiza los stocks POP e ISS mediante integración discreta tipo Euler.
        Fórmulas:
            POP(t) = POP(t-1) + (BR - DR) * Δt
            ISS(t) = ISS(t-1) + NSR * Δt
        Donde:
        - BR = nacimientos por año
        - DR = defunciones por año
        - NSR = nuevos suscriptores por año     
    Se ejecuta una vez por cada año de simulación.
     */
    private void updateStocks(double BR, double DR, double NSR) {
        // CÁLCULO DE LA INTEGRAL DE POBLACIÓN (POP)
        double flujoNetoPOP = BR - DR; 
        // Integración discreta: POP(t) = POP(t-1) + (B - D) * DeltaT
        this.POP = this.POP + flujoNetoPOP * DELTA_T;

        // CÁLCULO DE LA INTEGRAL DE SUSCRIPTORES (ISS)
        // Integración discreta: ISS(t) = ISS(t-1) + NSR * DeltaT
        this.ISS = this.ISS + NSR * DELTA_T;
    }
    
      public double updateAvailableWater() {
        // CÁLCULO DE LA INTEGRAL DE AGUA DISPONIBLE (AW)
        this.AW = this.AW + WB * DELTA_T; // AW(t) = AW(t-1) + WB * 1
        return this.AW;
    }

    public double getAW() {
        return AW;
    }
}
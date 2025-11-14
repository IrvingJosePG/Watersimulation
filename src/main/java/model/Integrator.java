package model;

    /* La clase Integrator ejecuta las integraciones del modelo de dinámica de sistemas.
       Aquí se calculan los stocks que cambian año con año:
        - Población (POP)
        - Número de suscriptores industriales y de servicios (ISS)
        - Agua disponible (AW)
 
       Además, calcula:
        - Balance hídrico (WB) = (oferta WS) – (demanda WD)
        
        El integrador utiliza integración discreta tipo Euler:
            Stock(t) = Stock(t-1) + Flujo * Δt   ,  Δt = 1 año en este modelo.
    */

public class Integrator{
    double POP = Parameters.POP_INITIAL;
    double ISS = Parameters.ISS_INITIAL;
    double AW = Parameters.AW_INITIAL;
    double WB;
    double BR = Parameters.BR;
    double DR= Parameters.DR;
    double DELTA_T = Parameters.DELTA_T;
    double PR = Parameters.PR;
    double ATR = Parameters.ATR;
    
    public double AvailableWater() {
        // CÁLCULO DE LA INTEGRAL DE AGUA DISPONIBLE (AW)
        this.AW = this.AW + WB * DELTA_T; // AW(t) = AW(t-1) + WB * 1
        return this.AW;
    }

    /* Calcula y retorna el balance hídrico del año, y ANTES actualiza todos los stocks
       que afectan el balance (población y suscriptores).
     
     * Fórmulas:
     *  1. WB = Water Supply - Water Demand */
    public double Waterbalance(double PR){
        actualizarStocksDeCrecimiento();
        double WD = Equations.totalWaterDemand(POP, ISS);
        double WS = Equations.totalWatersupply(PR , ATR);
        this.WB = WS - WD; // WB = WS - WD
        return WB;
    }

    public double getAW() {
        return this.AW;
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
    
    public void actualizarStocksDeCrecimiento(){
        // CÁLCULO DE LA INTEGRAL DE POBLACIÓN (POP)
        double flujoNetoPOP = this.BR - this.DR; 
        // Integración discreta: POP(t) = POP(t-1) + (B - D) * DeltaT
        this.POP = this.POP + flujoNetoPOP * DELTA_T;

        // CÁLCULO DE LA INTEGRAL DE SUSCRIPTORES (ISS)
        // Integración discreta: ISS(t) = ISS(t-1) + NSR * DeltaT
        this.ISS = this.ISS + Parameters.NSR * DELTA_T;
    }
}
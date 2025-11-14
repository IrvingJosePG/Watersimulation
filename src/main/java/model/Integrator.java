package model;

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
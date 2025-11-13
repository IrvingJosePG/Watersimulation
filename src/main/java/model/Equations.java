package model;

public class Equations{
    public static double totalWaterDemand(double pop, double iss) {
        double DU = Parameters.ADWCP * pop;
        double NDW = Parameters.NWCP * pop * 0.3;
        double ANU = Parameters.NWCP * pop * 0.7;
        double AISU = iss * Parameters.ISWCP * Parameters.FRAC_IS_REAL;
        double ISW = iss * Parameters.ISWCP * Parameters.FRAC_IS_WASTE;
        return DU + NDW + ANU + AISU + ISW;
    }

    public static double totalWatersupply(){
        double WS = Parameters.PR + Parameters.ATR;
        return WS;
    }
}

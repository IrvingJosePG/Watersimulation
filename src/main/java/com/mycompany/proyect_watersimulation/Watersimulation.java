package com.mycompany.proyect_watersimulation;
/**
 *
 * @author irvingjose
 */

import graphics.WaterCharts;
import java.text.DecimalFormat;
import model.*; // Importar la clase de formato

public class Watersimulation {
    public static void main(String[] args) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00"); 
        
        Integrator integrator = new Integrator(); 
        
        int anioInicial = 2020;
        int numAnios = 100;
        double[] years = new double[numAnios];
        double[] availableWater = new double[numAnios];
        double[] waterBalance = new double[numAnios];
        
        for (int i = 0; i < numAnios; i++) {
            int anioActual = anioInicial + i;
            years[i] = anioActual;
            availableWater[i] = integrator.AvailableWater();
            waterBalance[i] = integrator.Waterbalance();
        }
        WaterCharts.showCharts(years,waterBalance,availableWater);
        
        System.out.println("--- ESCENARIO BASE OAXACA");
        System.out.println("Año | WB (Balance Hídrico m^3) | AW (Stock Final m^3)");
        
        // Imprimir stock inicial (Año 2025)
        System.out.println(anioInicial + " | " + formatter.format(integrator.Waterbalance()) + " | " + formatter.format(integrator.getAW())); 

        for (int i = 1; i <= numAnios; i++) {
            int anioActual = anioInicial + i;
            
            // Ejecutar la integración para el siguiente año
            double WB = integrator.Waterbalance();
            double AW = integrator.AvailableWater();
      
            // --- 2. APLICACIÓN DEL FORMATO ---
            System.out.println(anioActual + " | " + formatter.format(WB) + " | " + formatter.format(AW));
        }
    }
}

Water Simulation – Modelo de Simulación del Agua en Oaxaca

Este proyecto es una aplicación en Java que simula la disponibilidad de agua (Agua Disponible – AW) y el Balance
Hídrico – WB para la ciudad de Oaxaca, utilizando diferentes escenarios y variables sensibles (PR, NWCP, ISWCP, 
BR, NSR).

?Cómo ejecutar el proyecto

La aplicación se ejecuta desde la clase principal:

com.mycompany.proyect_watersimulator.WaterSimulator

navega a:   src/main/java/com/mycompany/proyect_watersimulator/WaterSimulator.java


Se abrirá la interfaz principal del simulador, desde donde podrás acceder a los escenarios, gráficas e interpretaciones.

Estructura del proyecto

graphics/ → Gráficas generadas (AW y WB) con JFreeChart.

interpretations/ → Ventanas con explicaciones de los escenarios.

model/ → Estructuras internas utilizadas para la simulación.

scenariresults/ → Ventanas de resultados para cada escenario.

scenarios/ → Lógica de cálculo para cada variable sensible.

utils/ → Funciones auxiliares.

WaterSimulator.java → Clase principal.
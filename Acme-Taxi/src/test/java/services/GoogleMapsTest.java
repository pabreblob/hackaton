
package services;

import java.util.List;

import utilities.GoogleMaps;

public class GoogleMapsTest {

	public static void main(final String[] args) {
		final String origen = "Calle Bami, Sevilla, Espa�a";
		final String destino = "Nervi�n, Sevilla, Espa�a ";
		final List<Integer> data = GoogleMaps.getDistanceAndDuration(origen, destino);
		if (data == null)
			System.out.println("Error al leer el origen o el destino");
		else {
			System.out.println("Origen: " + origen);
			System.out.println("Destino: " + destino);
			System.out.println("Distancia: " + data.get(0) * 1.0 / 1000 + " km");
			System.out.println("Duraci�n: " + data.get(1) * 1.0 / 60 + " minutos");
		}
	}
}

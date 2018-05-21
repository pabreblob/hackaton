
package utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import services.ConfigurationService;

import com.google.gson.Gson;

import domain.maps.Data;

public class GoogleMaps {

	@Autowired
	private static ConfigurationService	configurationService;


	private static String getData(final String origin, final String destination) {
		final String key = "";
		final String uri = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + origin + "&destinations=" + destination + "&key=" + key;
		final RestTemplate restTemplate = new RestTemplate();
		final String result = restTemplate.getForObject(uri, String.class);
		return result;
	}

	/**
	 * Método que calcula distancia y dirección entre dos puntos dados
	 * 
	 * @param origin
	 *            String que indica el origen
	 * @param destination
	 *            String que indica el destino
	 * @return
	 *         Devuelve una lista de dos números. El primero es la distancia en metros (Integer) y
	 *         el segundo es la duración en segundos (Integer).
	 *         En caso de error, por ejemplo al no existir uno de los lugares indicados, se devolverá null
	 */
	public static List<Integer> getDistanceAndDuration(final String origin, final String destination) {
		if (GoogleMaps.configurationService.find().isUseApi())
			try {
				final String json = GoogleMaps.getData(origin, destination);
				final Data data = new Gson().fromJson(json, Data.class);
				final List<Integer> res = new ArrayList<>();
				res.add(0, data.getRows().get(0).getElements().get(0).getDistance().getValue());
				res.add(1, data.getRows().get(0).getElements().get(0).getDuration().getValue());
				return res;
			} catch (final Throwable oops) {
				return null;
			}
		else {
			final List<Integer> res = new ArrayList<>();
			res.add(0, 1);
			res.add(1, 1);
			return res;
		}
	}
}

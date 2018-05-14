
package utilities;

import org.springframework.web.client.RestTemplate;

public class GoogleMaps {

	public static String getData(final String origin, final String destination) {
		final String key = "";
		final String uri = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + origin + "&destinations=" + destination + "&key=" + key;
		final RestTemplate restTemplate = new RestTemplate();
		final String result = restTemplate.getForObject(uri, String.class);
		return result;
	}
}

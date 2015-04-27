//API_TEST_BRANDON MCDONALD
//April 24th, 2015
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONOutput {

	public static Station getStationAddressAndId(String name) throws Exception {
		// Create Station bean to hold Station Id and Station Address so we can
		// access these in Junit Test
		Station output = new Station();
		// GET JSON object from specific URI
		JSONObject o = getJSONObject("/nrel/alt-fuel-stations/v1/nearest.json");

		// GET all stations from o, then iterate over then to find station with
		// name argument
		JSONArray arr = o.getJSONArray("fuel_stations");
		for (int i = 0; i < arr.length(); i++) {
			JSONObject fuel_station = (JSONObject) arr.get(i);
			String stationName = (String) fuel_station.get("station_name");
			// check for station name here
			if (stationName.equals(name)) {
				String stationId = fuel_station.get("id").toString();
				JSONObject station = (JSONObject) getStationByID(stationId)
						.get("alt_fuel_station");

				// set output in the bean
				output.setId(stationId);
				output.setAddress(stationName + " "
						+ station.getString("street_address").toString() + " "
						+ station.getString("city").toString() + " "
						+ station.getString("state").toString() + " "
						+ station.getString("zip").toString());
			}
		}
		return output;
	}

	private static JSONObject getStationByID(String id)
			throws URISyntaxException, ClientProtocolException, IOException,
			JSONException {
		JSONObject o = getJSONObject("/nrel/alt-fuel-stations/v1/" + id
				+ ".json");
		return o;
	}

	private static JSONObject getJSONObject(String path)
			throws URISyntaxException, ClientProtocolException, IOException,
			JSONException {
		URI uri = new URIBuilder()
				.setScheme("http")
				.setHost("api.data.gov")
				.setPath(path)
				.setParameter("api_key",
						"7UrVxtr59Evtz0GCUVl7oSSI40STljybNB8z25w8")
				.setParameter("location", "Austin, Texas")
				.setParameter("ev_network", "ChargePoint Network").build();

		// declared GET request
		HttpGet httpget = new HttpGet(uri);

		// This is what you use to perform your http calls
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// created JSON object response
		CloseableHttpResponse response = httpclient.execute(httpget);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		// put result into StringBuffer
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		JSONObject o = new JSONObject(result.toString());

		return o;
	}
}
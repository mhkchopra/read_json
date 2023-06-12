import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class url {
    public static void main(String[] args) {
        try {
            // Specify the URL of the JSON file
            String url = "https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false";

            // Make a HTTP GET request to the URL
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();
            connection.disconnect();

            // Parse the JSON response
            JSONObject jsonObject = new JSONObject(responseBuilder.toString());

            // Validate the first text: "Name":"Carbon credits"
            String name1 = jsonObject.getString("Name");

            if (name1.equals("Carbon credits")) {
                System.out.println("Validation for text 1: Passed");
            } else {
                System.out.println("Validation for text 1: Failed");
            }

            // Validate the second text: "CanRelist = true"
            boolean canRelist = jsonObject.getBoolean("CanRelist");

            if (canRelist) {
                System.out.println("Validation for text 2: Passed");
            } else {
                System.out.println("Validation for text 2: Failed");
            }

            // Search and validate the third text: "Name":"Gallery","Description":"Good position in category"
            JSONArray promotions = jsonObject.getJSONArray("Promotions");
            boolean found = false;
            for (int i = 0; i < promotions.length(); i++) {
                JSONObject promotion = promotions.getJSONObject(i);
                String name2 = promotion.getString("Name");
                String description = promotion.getString("Description");
                if (name2.equals("Gallery") && description.equals("Good position in category")) {
                    found = true;
                    break;
                }
            }

            if (found) {
                System.out.println("Validation for text 3: Passed");
            } else {
                System.out.println("Validation for text 3: Failed");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

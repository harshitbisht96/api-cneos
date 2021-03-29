package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
public class Starter {
  public static void main(String[] args) {
    try {
      Starter.invoke();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void invoke() throws Exception {
    String url = "https://ssd-api.jpl.nasa.gov/fireball.api?date-min=2014-01-01&req-alt=true";
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    // optional default is GET
    con.setRequestMethod("GET");
    //add request header
    con.setRequestProperty("User-Agent", "Mozilla/5.0");
    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'GET' request to URL : " + url);
    System.out.println("Response Code : " + responseCode);
    BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    //print in String
//    System.out.println(response.toString());
    //Read JSON response and print
    JSONObject myResponse = new JSONObject(response.toString());
    System.out.println(myResponse.get("fields"));
    JSONArray dataArray = (JSONArray)myResponse.get("data");


    // Converting list to java list
    List<Fields> fieldsList = new ArrayList<Fields>();
    for (int i=0; i<dataArray.length(); i++) {
      Fields curField = new Fields();
      //Mapping to class object
      curField.setDate((dataArray.getJSONArray(i)).getString(0));
      curField.setEnergy((dataArray.getJSONArray(i)).getInt(1));
      curField.setImpactE((dataArray.getJSONArray(i)).getInt(2));
      curField.setLat((dataArray.getJSONArray(i)).getString(3));
      curField.setLatDir((dataArray.getJSONArray(i)).getString(4));
      curField.setLon((dataArray.getJSONArray(i)).getString(5));
      curField.setLonDir((dataArray.getJSONArray(i)).getString(6));
      curField.setAlt((dataArray.getJSONArray(i)).getString(7));
      curField.setVel((dataArray.getJSONArray(i)).getString(8));
      fieldsList.add(curField);
    }

    //Sorting the list on basis of energy in descending order
    Collections.sort(fieldsList, new Comparator<Fields>() {
      @Override
      public int compare(Fields field1, Fields field2)
      {
        return field2.energy - field1.energy;
      }
    });
  }

}
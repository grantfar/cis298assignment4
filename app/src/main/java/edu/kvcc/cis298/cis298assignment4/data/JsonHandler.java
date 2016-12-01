package edu.kvcc.cis298.cis298assignment4.data;

import android.webkit.DownloadListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import edu.kvcc.cis298.cis298assignment4.Beverage;

/**
 * Created by gfarnsworth on 11/30/16.
 */

public final class JsonHandler {
    public static JSONArray getJsonArray(){
        try {

            URL json = new URL("http://barnesbrothers.homeserver.com/beverageapi/");
            HttpURLConnection getJson = (HttpURLConnection) json.openConnection();
            getJson.connect();
            InputStream readin = getJson.getInputStream();
            Scanner scanner = new Scanner(readin);
            String jsonStr = "";
            while (scanner.hasNext())
                jsonStr += scanner.nextLine();
            scanner.close();
            readin.close();
            getJson.disconnect();
            return new JSONArray(jsonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
    public static ArrayList<Beverage> ParseJSONBeverageArray(JSONArray BeverageJson){
        JSONObject tmp;
        ArrayList<Beverage> ReturnList = new ArrayList<>();
        try {

            for (int i = 0; i < BeverageJson.length(); i++)
            {
                tmp = BeverageJson.getJSONObject(i);
                ReturnList.add(new Beverage(tmp.getString("id"),tmp.getString("name"),tmp.getString("pack"),
                        Double.parseDouble(tmp.getString("price")),tmp.getInt("active")==1));

            }
            return ReturnList;

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

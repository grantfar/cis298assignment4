package edu.kvcc.cis298.cis298assignment4.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import edu.kvcc.cis298.cis298assignment4.Beverage;
import edu.kvcc.cis298.cis298assignment4.R;

/**
 * Created by gfarnsworth on 11/30/16.
 */
public final class JsonHandler {
    public static List<Beverage> getJsonArray(){
        URL JsonUrl;
        try {
           JsonUrl = new URL("http://barnesbrothers.homeserver.com/beverageapi/");
        } catch (MalformedURLException e) {
            Log.e("JSON","MalformedURLException");
            return null;
        }
        HttpURLConnection connection;
        InputStream inputStream;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            connection = (HttpURLConnection) JsonUrl.openConnection();
            inputStream = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with http://barnesbrothers.homeserver.com/beverageapi/" );
            }
            byte[] buffer = new byte[1024];
            int BytesRead = 0;
            while ((BytesRead = inputStream.read(buffer))>0)
            {
                out.write(buffer,0,BytesRead);
            }
            inputStream.close();
        } catch (IOException e) {
            Log.e("JSON","IOException");
        }
        try {
            return ParseJSONBeverageArray(new JSONArray(new String(out.toByteArray())));
        } catch (JSONException e) {
            Log.e("JSON","JSONException");
            return null;
        }
    }


    //Parses JSON Array to Beverage List
    private static List<Beverage> ParseJSONBeverageArray(JSONArray BeverageJson){
        JSONObject tmp;
        List<Beverage> ReturnList = new ArrayList<>();
        try {

            for (int i = 0; i < BeverageJson.length(); i++)
            {
                tmp = BeverageJson.getJSONObject(i);
                ReturnList.add(new Beverage(tmp.getString("id"),tmp.getString("name"),tmp.getString("pack"),
                        Double.parseDouble(tmp.getString("price")),tmp.getInt("isActive")==1));

            }
            return ReturnList;

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

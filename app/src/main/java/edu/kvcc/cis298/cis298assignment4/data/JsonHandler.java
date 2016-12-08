package edu.kvcc.cis298.cis298assignment4.data;

import android.content.Context;
import android.util.Log;
import android.webkit.DownloadListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.kvcc.cis298.cis298assignment4.updatable;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import edu.kvcc.cis298.cis298assignment4.Beverage;

/**
 * Created by gfarnsworth on 11/30/16.
 */
public final class JsonHandler {
    private static List<Beverage> Output;
    public static void getJsonArray(List<Beverage> output, Context context, updatable Update){
        Cache requestCache = new DiskBasedCache(context.getCacheDir(),1024*1024);
        Network bevNetwork = new BasicNetwork(new HurlStack());
        RequestQueue bevLoader = new RequestQueue(requestCache,bevNetwork);
        bevLoader.start();
        final updatable toUpdate = Update;
        Output = output;
        StringRequest bevJson = new StringRequest(Request.Method.GET, "http://barnesbrothers.homeserver.com/beverageapi/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("json", response);
                try
                {
                    Output.addAll(JsonHandler.ParseJSONBeverageArray(new JSONArray(response)));
                    toUpdate.update();
                }
                catch (JSONException e){
                    Log.e("json","Invalid Json");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("json", "didn't work");
            }
        });

        bevLoader.add(bevJson);
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

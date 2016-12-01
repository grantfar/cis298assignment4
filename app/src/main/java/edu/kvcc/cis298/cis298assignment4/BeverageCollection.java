package edu.kvcc.cis298.cis298assignment4;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kvcc.cis298.cis298assignment4.data.JsonHandler;

/**
 * Created by David Barnes on 11/3/2015.
 * This is a singleton that will store the data for our application
 */
public class BeverageCollection {

    //Static variable that represents this class
    private static BeverageCollection sBeverageCollection;

    //private variable for the context that the singleton operates in
    private Context mContext;

    //List to store all of the beverages
    private List<Beverage> mBeverages;

    //public static method to get the single instance of this class
    public static BeverageCollection get(Context context) {
        //If the collection is null
        if (sBeverageCollection == null) {
            //make a new one
            sBeverageCollection = new BeverageCollection(context);
        }
        //regardless of whether it was just made or not, return the instance
        return sBeverageCollection;
    }

    //Private constructor to create a new BeverageCollection
    private BeverageCollection(Context context) {
        //Make a new list to hold the beverages
        mBeverages = new ArrayList<>();
        //Set the context to the one that is passed in
        mContext = context;
        //Call the private method to load the beverage list
        loadBeverageList();
    }

    //Getters
    public List<Beverage> getBeverages() {
        return mBeverages;
    }

    public Beverage getBeverage(String Id) {
        for (Beverage beverage : mBeverages) {
            if (beverage.getId().equals(Id)) {
                return beverage;
            }
        }
        return null;
    }

    //Method to load the beverage list from a CSV file
    private void loadBeverageList() {
        mBeverages = JsonHandler.ParseJSONBeverageArray(JsonHandler.getJsonArray());

    }
}

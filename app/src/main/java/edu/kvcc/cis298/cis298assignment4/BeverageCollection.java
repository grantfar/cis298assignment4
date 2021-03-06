package edu.kvcc.cis298.cis298assignment4;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import edu.kvcc.cis298.cis298assignment4.data.BeverageCursorWrapper;
import edu.kvcc.cis298.cis298assignment4.data.BeverageDBHelper;
import edu.kvcc.cis298.cis298assignment4.data.BeverageSchema;
import edu.kvcc.cis298.cis298assignment4.data.BeverageSchema.BeverageTable;
import edu.kvcc.cis298.cis298assignment4.data.JsonHandler;

/**
 * Created by David Barnes on 11/3/2015.
 * This is a singleton that will store the data for our application
 */
public class BeverageCollection{

    //Static variable that represents this class
    private static BeverageCollection sBeverageCollection;

    //private variable for the context that the singleton operates in
    private Context mContext;

    private SQLiteDatabase mBeverageDataBase;

    private boolean mDataLoaded;

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
        //Set the context to the one that is passed in
        mContext = context;
        //Call the private method to load the beverage list
        mBeverageDataBase = new BeverageDBHelper(mContext).getWritableDatabase();
    }

    //Getters
    public List<Beverage> getBeverages() {
        List<Beverage> beverages = new ArrayList<>();
        BeverageCursorWrapper allBeverage = new BeverageCursorWrapper(mBeverageDataBase.query(BeverageTable.NAME, null, null, null, null, null, null));
        allBeverage.toFirst();
        while (allBeverage.hasContent()) {
            beverages.add(allBeverage.getCurrent());
            allBeverage.moveToNext();
        }
        allBeverage.closeWrapper();
        return beverages;
    }

    public Beverage getBeverage(String Id) {
        BeverageCursorWrapper theCursor = new BeverageCursorWrapper(mBeverageDataBase.query(BeverageTable.NAME,null,
        BeverageTable.Cols.ID + " = ?",new String[]{Id},null,null,null));
        theCursor.toFirst();
        Beverage beverage = theCursor.getCurrent();
        theCursor.closeWrapper();
        return beverage;
    }

    //updates beverage in database
    public void updateBeverage(Beverage b){
        mBeverageDataBase.update(BeverageTable.NAME,getContentValues(b),BeverageTable.Cols.ID + " = ?",new String[]{b.getId()});
    }

    //Method to load the beverage list from a CSV file


    private ContentValues getContentValues(Beverage b){
        ContentValues values = new ContentValues();
        values.put(BeverageTable.Cols.ID,b.getId());
        values.put(BeverageTable.Cols.NAME,b.getName());
        values.put(BeverageTable.Cols.PACK,b.getPack());
        values.put(BeverageTable.Cols.PRICE,b.getPrice());
        values.put(BeverageTable.Cols.ACTIVE,b.isActive()?1:0);
        return values;
    }

    public void add(Beverage b){
        mBeverageDataBase.insert(BeverageTable.NAME,null,getContentValues(b));
    }

    public static abstract class BeverageCollectingGetter extends AsyncTask<Context,Void,BeverageCollection>{
        @Override
        protected BeverageCollection doInBackground(Context... contexts) {
            BeverageCollection tmp = BeverageCollection.get(contexts[0]);
            return tmp;
        }

        @Override
        protected abstract void onPostExecute(BeverageCollection beverageCollection);
    }

}

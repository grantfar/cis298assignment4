package edu.kvcc.cis298.cis298assignment4.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.kvcc.cis298.cis298assignment4.Beverage;
import edu.kvcc.cis298.cis298assignment4.BeverageCollection;
import edu.kvcc.cis298.cis298assignment4.data.BeverageSchema.BeverageTable;
import edu.kvcc.cis298.cis298assignment4.updatable;

/**
 * Created by gfarnsworth on 12/6/16.
 */

public class BeverageDBHelper extends SQLiteOpenHelper implements updatable {
    private boolean mImport;
    private updatable mUpdate;
    private Context mContext;
    private List<Beverage> mImportedBeverages;

    private static final String DATABASE_NAME = "beverages.db";
    private static final int VERSION = 1;

    public BeverageDBHelper(Context context, updatable Update) {
        super(context, DATABASE_NAME, null, VERSION);
        mUpdate = Update;
        mImport = false;
        mContext = context;
        mImportedBeverages = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" Create Table " + BeverageTable.NAME + " ( " +
        BeverageTable.Cols.ID + " TEXT , " +
        BeverageTable.Cols.NAME + " TEXT , " +
        BeverageTable.Cols.PACK + " TEXT , " +
        BeverageTable.Cols.PRICE + " REAL ," +
        BeverageTable.Cols.ACTIVE + "INTEGER);");
        mImport = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(mImport){
            JsonHandler.getJsonArray(mImportedBeverages,mContext,this);
        }
        else{
            mUpdate.update();
        }
    }

    @Override
    public void update() {
        for (Beverage b:mImportedBeverages)
        {
            BeverageCollection.get(mContext,null).add(b);
        }
        mUpdate.update();
    }
}

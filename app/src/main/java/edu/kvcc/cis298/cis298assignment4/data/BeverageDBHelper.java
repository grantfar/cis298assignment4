package edu.kvcc.cis298.cis298assignment4.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import edu.kvcc.cis298.cis298assignment4.Beverage;
import edu.kvcc.cis298.cis298assignment4.BeverageCollection;
import edu.kvcc.cis298.cis298assignment4.data.BeverageSchema.BeverageTable;

/**
 * Created by gfarnsworth on 12/6/16.
 */

public class BeverageDBHelper extends SQLiteOpenHelper {
    private boolean mImport;
    private Context mContext;
    private List<Beverage> mImportedBeverages;
    private static final String DATABASE_NAME = "beverages.db";
    private static final int VERSION = 1;


    public BeverageDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mImport = false;
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" Create Table " + BeverageTable.NAME + " ( " +
        " _id integer primary key autoincrement, " +
        BeverageTable.Cols.ID + " TEXT , " +
        BeverageTable.Cols.NAME + " TEXT , " +
        BeverageTable.Cols.PACK + " TEXT , " +
        BeverageTable.Cols.PRICE + " REAL ," +
        BeverageTable.Cols.ACTIVE + " INT);");
        mImport = true;
        importData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    private void importData(SQLiteDatabase db){
        mImportedBeverages = JsonHandler.getJsonArray();
        for (Beverage b:mImportedBeverages) {
            ContentValues values = new ContentValues();
            values.put(BeverageTable.Cols.ID,b.getId());
            values.put(BeverageTable.Cols.NAME,b.getName());
            values.put(BeverageTable.Cols.PACK,b.getPack());
            values.put(BeverageTable.Cols.PRICE,b.getPrice());
            values.put(BeverageTable.Cols.ACTIVE,b.isActive()?1:0);
            db.insert(BeverageTable.NAME,null,values);
        }
    }


}

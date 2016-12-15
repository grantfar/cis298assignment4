package edu.kvcc.cis298.cis298assignment4.data;

import android.database.Cursor;

import edu.kvcc.cis298.cis298assignment4.Beverage;
import edu.kvcc.cis298.cis298assignment4.data.BeverageSchema.BeverageTable;

/**
 * Created by gfarnsworth on 12/6/16.
 */

public class BeverageCursorWrapper {
    private Cursor mCursor;
    public BeverageCursorWrapper(Cursor cursor){
        mCursor = cursor;
    }

    public void toFirst(){
        mCursor.moveToFirst();
    }

    public Beverage getCurrent(){
        return rowToBeverage();
    }

    public Beverage getNext(){
        mCursor.moveToNext();
        return rowToBeverage();
    }

    public void closeWrapper(){
        mCursor.close();
    }

    public void moveToNext(){
        mCursor.moveToNext();
    }

    public boolean hasContent(){
        return !mCursor.isAfterLast();
    }

    private Beverage rowToBeverage(){

        String Id = mCursor.getString(mCursor.getColumnIndex(BeverageTable.Cols.ID));
        String Name = mCursor.getString(mCursor.getColumnIndex(BeverageTable.Cols.NAME));
        String Pack = mCursor.getString(mCursor.getColumnIndex(BeverageTable.Cols.PACK));
        Double Price = mCursor.getDouble(mCursor.getColumnIndex(BeverageTable.Cols.PRICE));
        Boolean Active = (1 == mCursor.getInt(mCursor.getColumnIndex(BeverageTable.Cols.ACTIVE)));
        return new Beverage(Id,Name,Pack,Price,Active);
    }
}

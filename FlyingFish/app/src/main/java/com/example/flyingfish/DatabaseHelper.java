package com.example.flyingfish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mylist.db";                         //name of the database file created with .db extension
    public static final String TABLE_NAME = "mylist_data";                          //name of the table created inside the database
    public static final String COL1 = "ID";                                         //First column name which is unique or primary key
    public static final String COL2 = "ITEM1";                                      //Second column name which store score

    public DatabaseHelper(Context context) {                                        //default constructor used to call this class
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {                                       //on_create method will create a empty table
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,ITEM1 NUMBER)";
        db.execSQL(createTable);                                                    //execute SQL query
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {                    //when we upgrade the database to new version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);                                         //drop if any old table exists
        onCreate(db);                                                                             //again onCreate method
    }

    public void addData(Integer item1) {                                                          //method for insertion into table
        SQLiteDatabase db = this.getWritableDatabase();                                           //object db in writable mode
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);                                                           //adding column for scores
        db.insert(TABLE_NAME, null, contentValues);                                //lastly insert into the table

    }

    public Cursor getListContents() {                                                            //method for viewing the table
        SQLiteDatabase db = this.getReadableDatabase();                                          //object db in readable mode
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);         //selects all from table
        return data;                                                                             //returns the cursor data
    }

    public void updateDatabse(Integer id, Integer item1) {                                       //method for updating the table
        SQLiteDatabase db = this.getWritableDatabase();                                          //object db in writable mode
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);                                                             //update the particular id
        contentValues.put(COL2, item1);                                                          //with score
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id.toString()});   //update to the matching id
    }

    public Cursor minElementID() {                                                              //method finds the min value of col2 from the table
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor id = db.rawQuery("SELECT * FROM mylist_data WHERE ITEM1=(SELECT MIN(ITEM1) FROM mylist_data)", null);
        return id;                                                                              //returns the cursor id
    }

    public Cursor descOrder() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor desc = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL2 + " DESC", null);
        return desc;                                                                            //returns the score in descending order
    }

}

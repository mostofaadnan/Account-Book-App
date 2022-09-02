package com.foodorder.accountbook.Database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import com.foodorder.accountbook.Fragment.CurrencySetup;

public class CurrencyDb extends SQLiteOpenHelper {
    private static String DB_PATH = "";
    private static String DB_NAME = "currency.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private SQLiteOpenHelper sqLiteOpenHelper;

    public CurrencyDb(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH = myContext.getDatabasePath(DB_NAME)
                .toString();
    }

    public void createDataBase()
            throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
        } else {

            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error(
                        "Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH;
            checkDB
                    = SQLiteDatabase
                    .openDatabase(
                            myPath, null,
                            SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {

            // database doesn't exist yet.
            Log.e("message", "" + e);
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase()
            throws IOException {
        // Open your local db as the input stream
        InputStream myInput
                = myContext.getAssets()
                .open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH;

        // Open the empty db as the output stream
        OutputStream myOutput
                = new FileOutputStream(outFileName);

        // transfer bytes from the
        // inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase()
            throws SQLException {
        // Open the database
        String myPath = DB_PATH;
        myDataBase = SQLiteDatabase
                .openDatabase(
                        myPath, null,
                        SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        // close the database.
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // It is an abstract method
        // but we define our own method here.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // It is an abstract method which is
        // used to perform different task
        // based on the version of database.
    }

    public List<String> CurrencyList() {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from curency", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return list;
    }

    public Cursor getSelectedCurency() {

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from SelectCurrency where id=1", null);
        if (cursor != null)
            cursor.moveToFirst();
        database.close();
        return cursor;

    }

    public Cursor getSelectedCurencyItem(String curencyName) {

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from curency where currency_name=?",
                new String[]{curencyName});
        if (cursor != null)
            cursor.moveToFirst();
        database.close();
        return cursor;

    }

    public boolean CurencyUpdate(String curencyname, String curency_code, String curency_symbole, String use) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("currency_name", curencyname);
        value.put("currency_code", curency_code);
        value.put("currency_symbol", curency_symbole);
        value.put("use_code", use);

        long row = database.update("SelectCurrency", value, "id=1", null);
        if (row <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getSelectPassCode() {

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from Passcode where id=1", null);
        if (cursor != null)
            cursor.moveToFirst();
        database.close();
        return cursor;

    }

    public boolean PinCodeAccessTrue() {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("Passcode_access", 1);

        long row = database.update("Passcode", value, "id=1", null);
        if (row <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean PinCodeAccessFalse() {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("Passcode_access", 0);
        value.put("passcode", "");
        long row = database.update("Passcode", value, "id=1", null);
        if (row <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean PinCodeSeup(String passcode) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("passcode", passcode);
        value.put("Passcode_access", 1);
        long row = database.update("Passcode", value, "id=1", null);
        if (row <= 0) {
            return false;
        } else {
            return true;
        }
    }
}


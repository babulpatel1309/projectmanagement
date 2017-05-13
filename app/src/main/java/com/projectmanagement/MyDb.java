package com.projectmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.password;

/**
 * Created by Babul Patel on 30/03/2017.
 */

public class MyDb extends SQLiteOpenHelper {

    public static final String DB_NAME = "mydatabase";

    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASS = "password";
    public static final String COLUMN_CMPASS = " confirmpassword";
    public static final String COLUMN_NUM = "number";
    private String password;

    public MyDb(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASS + "TEXT,"
            + COLUMN_CMPASS + "TEXT,"
            + COLUMN_NUM + " NUMBER);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < newVersion) {
            onCreate(db);
        }
    }

    public void registerUser(String email, String password, String confirmpassword, String number) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASS, password);
        values.put(COLUMN_CMPASS, confirmpassword);
        values.put(COLUMN_NUM, number);

        long id = db.insert(USER_TABLE, null, values);
        db.close();

        final int d = Log.d(null, "user inserted" + id);
    }

    public boolean takeUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "Select * from " + USER_TABLE + "where " + COLUMN_EMAIL + " = '" + email + "' and " + COLUMN_PASS + " = '" + password + "'";

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        db.close();

        return false;

    }


}

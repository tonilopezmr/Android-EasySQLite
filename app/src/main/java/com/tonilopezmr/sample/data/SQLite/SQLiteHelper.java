package com.tonilopezmr.sample.data.SQLite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by toni on 05/02/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "com.tonilopezmr.sample.database";
    public final static int SQLITE_VERSION = 3;


    private final String SUBJECT_TABLE =
            "CREATE TABLE SUBJECT(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT NOT NULL" +
            ")";

    final private String FOREIGN_KEY_ON = "PRAGMA foreign_keys = ON";
    final private String DROP = "DROP TABLE IF EXISTS ";


    final private String SUBJECT = "SUBJECT";

    private static SQLiteHelper connection;

    private SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                         int version) {
        super(context, name, factory, version);
    }

    public static SQLiteHelper getHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                         int version){
        if (connection == null) {
            connection = new SQLiteHelper(context, name, factory, version);
        }

        return connection;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SUBJECT_TABLE);
        db.execSQL("INSERT INTO "+SUBJECT+"(name) VALUES ('Maths')");
        db.execSQL("INSERT INTO "+SUBJECT+"(name) VALUES ('Biology')");
        db.execSQL("INSERT INTO "+SUBJECT+"(name) VALUES ('Calculus')");
        Log.i(this.getClass().toString(), "CREATE VALUES");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP + SUBJECT);
        onCreate(db);
    }

}

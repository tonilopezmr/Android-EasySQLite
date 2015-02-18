package com.tonilopezmr.sample.data.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tonilopezmr.easysqlite.SQLiteHelper;
import com.tonilopezmr.easysqlite.exception.SQLiteHelperException;

/**
 *
 * @author Toni
 */
public class SQLiteManager {

    public final static String DATABASE_NAME = "com.tonilopezmr.sample.database";
    public final static int SQLITE_VERSION = 11;


    private static final String SUBJECT_TABLE =
            "CREATE TABLE SUBJECT(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT NOT NULL" +
                    ")";

    final static private String SUBJECT = "SUBJECT";

    final static private String[] TABLES = {SUBJECT_TABLE};
    final static private String[] TABLENAMES = {SUBJECT};

    private static SQLiteDatabase dataBase;

    private SQLiteManager(){}

    public static SQLiteDatabase getDataBase(Context context){
        if (dataBase == null){
            //Create the SQLiteOpenHelper
            SQLiteHelper conex = SQLiteHelper.builder()
                    .beginConfig()
                        .onCreateCallback(onCreateCallback)
                    .endConfig()
                    .tables(TABLES)
                    .tableNames(TABLENAMES)
                    .build(context, DATABASE_NAME, SQLITE_VERSION);
            dataBase = conex.getWritableDatabase();
        }
        return  dataBase;
    }

    private static SQLiteHelper.OnCreateCallback onCreateCallback = new SQLiteHelper.OnCreateCallback() {
        @Override
        public void onCreate(SQLiteDatabase db) throws SQLiteHelperException {
            db.execSQL(SUBJECT_TABLE);
            db.execSQL("INSERT INTO "+SUBJECT+"(name) VALUES ('Maths')");
            db.execSQL("INSERT INTO "+SUBJECT+"(name) VALUES ('Biology')");
            db.execSQL("INSERT INTO "+SUBJECT+"(name) VALUES ('Calculus')");
            Log.i(this.getClass().toString(), "CREATE VALUES");
        }
    };
}

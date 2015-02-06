package com.tonilopezmr.sample.data.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by toni on 05/02/15.
 */
public class SQLiteManager {

    private static SQLiteDatabase dataBase;

    private SQLiteManager(){}

    public static SQLiteDatabase getDataBase(Context context){
        if (dataBase == null){
            SQLiteHelper conex = SQLiteHelper.getHelper(context, SQLiteHelper.DATABASE_NAME,
                    null, SQLiteHelper.SQLITE_VERSION);
            dataBase = conex.getWritableDatabase();
        }
        return  dataBase;
    }

}

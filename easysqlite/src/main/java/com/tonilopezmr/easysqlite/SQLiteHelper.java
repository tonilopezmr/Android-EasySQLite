package com.tonilopezmr.easysqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tonilopezmr.easysqlite.exception.SQLiteHelperException;

/**
 * @author toni.
 */
public class SQLiteHelper extends SQLiteOpenHelper{

    private Builder builder;

    public SQLiteHelper(Builder builder) {
        super(builder.context, builder.databaseName, builder.factory, builder.databaseVersion);

        this.builder = builder;
    }

    public String getDatabaseName(){
        return builder.databaseName;
    }

    public int getDatabaseVersion(){
        return builder.databaseVersion;
    }

    public boolean isOnForeignKey(){
        return builder.isOnForeignKey;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            builder.onCreateCallback.onCreate(db);
        } catch (SQLiteHelperException e) {
            Log.e(this.getClass().toString(), Log.getStackTraceString(e), e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            builder.onUpdateCallback.onUpdate(db, oldVersion, newVersion);
        } catch (SQLiteHelperException e) {
            Log.e(this.getClass().toString(), Log.getStackTraceString(e), e);
        }
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder implements SQLiteHelperBuilder, SQLiteHelperCallback, ConfigBuilder{

        private Context context;
        private String databaseName;
        private SQLiteDatabase.CursorFactory factory;
        private int databaseVersion;

        private String[] tables;
        private String[] tableNames;

        private OnCreateCallback onCreateCallback;
        private OnUpdateCallback onUpdateCallback;

        private boolean isOnForeignKey;

        final private String FOREIGN_KEY_ON = "PRAGMA foreign_keys = ON";
        final private String DROP = "DROP TABLE IF EXISTS ";

        public Builder() {
            this.context = null;
            this.databaseName = "com.sqlitedatabase";
            this.databaseVersion = 1;
            this.factory = null;
            this.onUpdateCallback = this;
            this.onCreateCallback = this;

            //config
            this.isOnForeignKey = false;
        }

        @Override
        public SQLiteHelper build(Context context, String databaseName,
                                  SQLiteDatabase.CursorFactory factory, int databaseVersion) {
            this.context = context;
            this.databaseName = databaseName;
            this.factory = factory;
            this.databaseVersion = databaseVersion;
            return new SQLiteHelper(this);
        }

        @Override
        public SQLiteHelper build(Context context, String databaseName, int databaseVersion) {
            this.context = context;
            this.databaseName = databaseName;
            this.databaseVersion = databaseVersion;
            return new SQLiteHelper(this);
        }

        @Override
        public SQLiteHelper build(Context context) {
            this.context = context;
            return new SQLiteHelper(this);
        }


        @Override
        public SQLiteHelperBuilder tables(String[] tables) {
            this.tables = tables;
            return this;
        }

        @Override
        public SQLiteHelperBuilder tableNames(String[] tableNames) {
            this.tableNames = tableNames;
            return this;
        }

        @Override
        public SQLiteHelperBuilder version(int version) {
            this.databaseVersion = version;
            return this;
        }

        @Override
        public SQLiteHelperBuilder name(String name) {
            this.databaseName = name;
            return this;
        }

        private void executePragma(SQLiteDatabase db){
            if (isOnForeignKey) db.execSQL(FOREIGN_KEY_ON);
        }

        //Events
        @Override
        public void onCreate(SQLiteDatabase db) throws SQLiteHelperException {
            if(tables == null){
                throw new SQLiteHelperException("The array of String tables can't be null!!");
            }

            executePragma(db);

            for (String table : tables) {
                db.execSQL(table);
            }
        }

        /**
         * It is a default implementation of onUpdate.
         *
         *
         * @param db SQLiteDatabase db
         * @param oldVerison int Old version
         * @param newVersion int New version
         */
        @Override
        public void onUpdate(SQLiteDatabase db, int oldVerison, int newVersion) throws SQLiteHelperException {
            if (tableNames == null) {
                throw new SQLiteHelperException("The array of String tableNames can't be null!!");
            }


            for (int i = tableNames.length-1; i >= 0; i--) {
                db.execSQL(DROP + tableNames[i]);
            }

            onCreateCallback.onCreate(db);
        }

        //Config
        @Override
        public ConfigBuilder beginConfig() {
            return this;
        }

        @Override
        public ConfigBuilder foreignKey(boolean isOnForeignKey) {
            this.isOnForeignKey = isOnForeignKey;
            return this;
        }

        @Override
        public ConfigBuilder helperCallback(SQLiteHelperCallback callback) {
            this.onCreateCallback = callback;
            this.onUpdateCallback = callback;
            return this;
        }

        @Override
        public ConfigBuilder onCreateCallback(OnCreateCallback onCreateCallback) {
            this.onCreateCallback = onCreateCallback;
            return this;
        }

        @Override
        public ConfigBuilder onUpdateCallback(OnUpdateCallback onUpdateCallback) {
            this.onUpdateCallback = onUpdateCallback;
            return this;
        }

        @Override
        public SQLiteHelperBuilder endConfig() {
            return this;
        }
    }

    public interface OnCreateCallback{
        public void onCreate(SQLiteDatabase db) throws SQLiteHelperException;
    }

    public interface OnUpdateCallback{
        public void onUpdate(SQLiteDatabase db, int oldVerison, int newVersion) throws SQLiteHelperException;
    }

    public interface SQLiteHelperCallback extends  OnCreateCallback, OnUpdateCallback{
    }

    public interface SQLiteBuilder {
        //with CursorFactory
        public SQLiteHelper build(Context context, String databaseName,
                                  SQLiteDatabase.CursorFactory factory, int databaseVersion);
        //without CursorFactory
        public SQLiteHelper build(Context context, String databaseName, int databaseVersion);
        public SQLiteHelper build(Context context);
    }

    public interface ConfigBuilder{
        public ConfigBuilder foreignKey(boolean onOff);
        public ConfigBuilder beginConfig();
        public ConfigBuilder helperCallback(SQLiteHelperCallback callback);
        public ConfigBuilder onCreateCallback(OnCreateCallback onCreateCallback);
        public ConfigBuilder onUpdateCallback(OnUpdateCallback onUpdateCallback);
        public SQLiteHelperBuilder endConfig();
    }

    //concrete build
    public interface SQLiteHelperBuilder extends SQLiteBuilder{
        public ConfigBuilder beginConfig();
        public SQLiteHelperBuilder tables(String[] tables);
        public SQLiteHelperBuilder tableNames(String [] tableNames);
        public SQLiteHelperBuilder version(int version);
        public SQLiteHelperBuilder name(String name);
    }
}
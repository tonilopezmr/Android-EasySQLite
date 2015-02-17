package com.tonilopezmr.easysqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author toni.
 */
public class SQLiteHelper extends SQLiteOpenHelper{

    private SQLiteEvents events;

    public SQLiteHelper(Builder builder) {
        super(builder.context, builder.databaseName, builder.factory, builder.databaseVersion);

        events = builder.sqliteEvents;
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        events.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        events.onUpdate(db, oldVersion, newVersion);
    }

    public static class Builder implements SQLiteBuilder, SQLiteHelperBuilder, SQLiteEvents, ConfigBuilder{

        private Context context;
        private String databaseName;
        private SQLiteDatabase.CursorFactory factory;
        private int databaseVersion;

        private String[] tables;
        private String[] tableNames;

        private SQLiteEvents sqliteEvents;

        private boolean isOnForeignKey;

        final private String FOREIGN_KEY_ON = "PRAGMA foreign_keys = ON";
        final private String DROP = "DROP TABLE IF EXISTS ";

        public Builder(Context context) {
            this.context = context;
            this.databaseName = "com.example.sqlitedatabase";
            this.databaseVersion = 1;
            this.factory = null;
            this.sqliteEvents = this;

            //config
            this.isOnForeignKey = false;
        }

        @Override
        public SQLiteHelper build(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
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
        public SQLiteHelperBuilder tables(String[] tables) {
            this.tables = tables;
            return this;
        }

        @Override
        public SQLiteHelperBuilder tableNames(String[] tableNames) {
            this.tableNames = tableNames;
            return this;
        }

        //Events
        @Override
        public void onCreate(SQLiteDatabase db) {
            for (int i = 0; i < tables.length; i++){
                db.execSQL(tables[i]);
            }
        }

        @Override
        public void onUpdate(SQLiteDatabase db, int oldVerison, int newVersion) {
            for (int i = 0; i < tableNames.length; i++){
                db.execSQL(DROP + tableNames[i]);
            }
        }

        //Config
        @Override
        public ConfigBuilder foreignKey(boolean onOff) {
            return null;
        }

        @Override
        public ConfigBuilder events(SQLiteEvents events) {
            this.sqliteEvents = events;
            return this;
        }

        @Override
        public SQLiteHelperBuilder endConfig() {
            return null;
        }
    }

    public interface SQLiteEvents{
        public void onCreate(SQLiteDatabase db);
        public void onUpdate(SQLiteDatabase db, int oldVerison, int newVersion);
    }

    public interface SQLiteBuilder {
        //with CursorFactory
        public SQLiteHelper build(Context context, String databaseName,
                                  SQLiteDatabase.CursorFactory factory, int databaseVersion);
        //without CursorFactory
        public SQLiteHelper build(Context context, String databaseName, int databaseVersion);
    }

    public interface ConfigBuilder{
        public ConfigBuilder foreignKey(boolean onOff);
        public ConfigBuilder events(SQLiteEvents events);
        public SQLiteHelperBuilder endConfig();
    }

    //concrete build
    public interface SQLiteHelperBuilder {
        public SQLiteHelperBuilder tables(String[] tables);
        public SQLiteHelperBuilder tableNames(String [] tableNames);
    }
}

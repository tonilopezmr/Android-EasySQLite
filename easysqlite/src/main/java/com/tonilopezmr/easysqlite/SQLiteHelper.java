/*
 * Copyright 2015 Antonio López Marín <tonilopezmr.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tonilopezmr.easysqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tonilopezmr.easysqlite.exception.SQLiteHelperException;

/**
 * @author Antonio López Marín
 */
public final class SQLiteHelper extends SQLiteOpenHelper{

    private Builder builder;

    private SQLiteHelper(Builder builder) {
        super(builder.context, builder.databaseName, builder.factory, builder.databaseVersion);

        this.builder = builder;
    }

    /**
     * Database version.
     *
     * @return the version of database SQLite.
     */
    public int getDatabaseVersion(){
        return builder.databaseVersion;
    }

    /**
     * If the foreign keys in SQLite are enable.
     *
     * @return true if the foreign keys are enable or false if it is not the case.
     */
    public boolean isOnForeignKey(){
        return builder.isOnForeignKey;
    }

    private void executePragma(SQLiteDatabase db){
        if (builder.isOnForeignKey) db.execSQL(Builder.FOREIGN_KEY_ON);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            if(builder.tables == null){
                throw new SQLiteHelperException("The array of String tables can't be null!!");
            }
            executePragma(db);

            builder.onCreateCallback.onCreate(db);
        } catch (SQLiteHelperException e) {
            Log.e(this.getClass().toString(), Log.getStackTraceString(e), e);
        }
    }

    /**
     * Called every time.
     *
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        executePragma(db);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (builder.tableNames == null) {
                throw new SQLiteHelperException("The array of String tableNames can't be null!!");
            }

            builder.onUpgradeCallback.onUpgrade(db, oldVersion, newVersion);
        } catch (SQLiteHelperException e) {
            Log.e(this.getClass().toString(), Log.getStackTraceString(e), e);
        }
    }

    /**
     * Build SQLiteHelper.
     *
     * @return The new SQLiteHelperBuilder.
     */
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
        private OnUpgradeCallback onUpgradeCallback;

        private boolean isOnForeignKey;

        final static public String FOREIGN_KEY_ON = "PRAGMA foreign_keys = ON";
        final static public String DROP = "DROP TABLE IF EXISTS ";

        private Builder() {
            this.context = null;
            this.databaseName = "com.easysqlite";
            this.databaseVersion = 1;
            this.factory = null;
            this.onUpgradeCallback = this;
            this.onCreateCallback = this;

            //config
            this.isOnForeignKey = false;
        }

        /**
         * Build the SQLiteHelper with SQLiteDatabase.CursorFactory factory.
         *
         * @param context to use to open or create the database
         * @param databaseName of the database file, or null for an in-memory database
         * @param factory to use for creating cursor objects, or null for the default
         * @param databaseVersion number of the database (starting at 1); if the database is older,
         *     {@link #onUpgrade} will be used to upgrade the database; if the database is
         *     newer, {@link #onDowngrade} will be used to downgrade the database
         * @return new SQLiteHelper
         */
        @Override
        public SQLiteHelper build(Context context, String databaseName,
                                  SQLiteDatabase.CursorFactory factory, int databaseVersion) {
            this.context = context;
            this.databaseName = databaseName;
            this.factory = factory;
            this.databaseVersion = databaseVersion;
            return new SQLiteHelper(this);
        }

        /**
         * Build the SQLiteHelper with the minimum information, SQLiteDatabase.CursorFactory by default
         * is null.
         *
         * @param context to use to open or create the database
         * @param databaseName of the database file, or null for an in-memory database
         * @param databaseVersion number of the database (starting at 1); if the database is older,
         *     {@link #onUpgrade} will be used to upgrade the database; if the database is
         *     newer, {@link #onDowngrade} will be used to downgrade the database
         * @return new SQLiteHelper
         */
        @Override
        public SQLiteHelper build(Context context, String databaseName, int databaseVersion) {
            this.context = context;
            this.databaseName = databaseName;
            this.databaseVersion = databaseVersion;
            return new SQLiteHelper(this);
        }

        /**
         * Build the SQLiteHelper with the default values.
         *
         * The default values:
         *
         *      -  Database name: com.sqlitedatabase
         *
         *      -  Database version: 1
         *
         * If you wish increment the version, you must use the method {@link #version(int)}
         *
         * @param context to use to open or create the database
         * @return new SQLiteHelper
         */
        @Override
        public SQLiteHelper build(Context context) {
            this.context = context;
            return new SQLiteHelper(this);
        }

        /**
         * Set the create tables of database, this method is very important.
         *
         * Important:
         *
         *      -  The tables in database must be sorted in order of creation,
         *         to avoid problems with the foreign keys!.
         *
         *      -  The array of tables cannot be null.
         *
         * @param tables array of tables
         * @return SQLiteHelperBuilder with new set tables
         */
        @Override
        public SQLiteHelperBuilder tables(String[] tables) {
            this.tables = tables;
            return this;
        }

        /**
         * Set the table names of database, this method is very important.
         *
         * Important:
         *
         *      -  The table names in database must be sorted in opposite order
         *         by the array of creation tables.
         *
         *      -  The array of table names cannot be null.
         *
         * @param tableNames array of table names
         * @return SQLiteHelperBuilder with new set table names
         */
        @Override
        public SQLiteHelperBuilder tableNames(String[] tableNames) {
            this.tableNames = tableNames;
            return this;
        }

        /**
         * Set the version value of database.
         *
         * @param version number of the database (starting at 1); if the database is older,
         *     {@link #onUpgrade} will be used to upgrade the database; if the database is
         *     newer, {@link #onDowngrade} will be used to downgrade the database
         * @return SQLiteHelperBuilder with new set version
         */
        @Override
        public SQLiteHelperBuilder version(int version) {
            this.databaseVersion = version;
            return this;
        }

        /**
         * Set the name value of database.
         *
         * @param name of the database file, or null for an in-memory database
         * @return SQLiteHelperBuilder with new set name
         */
        @Override
        public SQLiteHelperBuilder name(String name) {
            this.databaseName = name;
            return this;
        }

        /**
         * Set the SQLiteDatabase.CursorFactory.
         *
         * @param factory to use for creating cursor objects
         * @return SQLiteHelperBuilder with new set factory
         */
        @Override
        public SQLiteHelperBuilder factory(SQLiteDatabase.CursorFactory factory) {
            this.factory = factory;
            return this;
        }

        /**
         * It is a default implementation of onCreate, only creates all tables.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(SQLiteDatabase db){
            for (String table : tables) {
                db.execSQL(table);
            }
        }

        /**
         * It is a default implementation of onUpgrade, only remove all tables and re-create the tables.
         *
         * @param db The database.
         * @param oldVersion int Old version
         * @param newVersion int New version
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            for (int i = tableNames.length-1; i >= 0; i--) {
                db.execSQL(DROP + tableNames[i]);
            }

            onCreateCallback.onCreate(db);
        }

        /**
         * Begin the Builder configuration.
         *
         * @return ConfigBuilder
         */
        @Override
        public ConfigBuilder beginConfig() {
            return this;
        }

        /**
         * Set the isOnForeignKey value, if you need to link tables.
         *
         * @param isOnForeignKey the boolean value to determinate if the foreign keys are enable or not.
         * @return ConfigBuilder
         */
        @Override
        public ConfigBuilder foreignKey(boolean isOnForeignKey) {
            this.isOnForeignKey = isOnForeignKey;
            return this;
        }

        /**
         * If you need change the default methods onCreate and onUpgrade.
         * 
         * @param callback SQLiteHelperCallback interface contains onCreate and onUpgrade methods.
         * @return ConfigBuilder
         */
        @Override
        public ConfigBuilder helperCallback(SQLiteHelperCallback callback) {
            this.onCreateCallback = callback;
            this.onUpgradeCallback = callback;
            return this;
        }

        /**
         * If you need change only the method onCreate, use this method.
         *
         * @param onCreateCallback OnCreateCallback interface contains onCreate method.
         * @return ConfigBuilder
         */
        @Override
        public ConfigBuilder onCreateCallback(OnCreateCallback onCreateCallback) {
            this.onCreateCallback = onCreateCallback;
            return this;
        }

        /**
         * If you need change only the method onUpgrade, use this method.
         *
         * @param onUpgradeCallback OnUpgradeCallback interface contains onUpgrade method.
         * @return ConfigBuilder
         */
        @Override
        public ConfigBuilder onUpgradeCallback(OnUpgradeCallback onUpgradeCallback) {
            this.onUpgradeCallback = onUpgradeCallback;
            return this;
        }

        /**
         * End the configuration.
         *
         * @return SQLiteHelperBuilder
         */
        @Override
        public SQLiteHelperBuilder endConfig() {
            return this;
        }
    }

    public interface OnCreateCallback{
        public void onCreate(SQLiteDatabase db);
    }

    public interface OnUpgradeCallback{
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

    public interface SQLiteHelperCallback extends  OnCreateCallback, OnUpgradeCallback{
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
        public ConfigBuilder helperCallback(SQLiteHelperCallback callback);
        public ConfigBuilder onCreateCallback(OnCreateCallback onCreateCallback);
        public ConfigBuilder onUpgradeCallback(OnUpgradeCallback onUpgradeCallback);
        public SQLiteHelperBuilder endConfig();
    }

    //concrete build
    public interface SQLiteHelperBuilder extends SQLiteBuilder{
        public ConfigBuilder beginConfig();
        public SQLiteHelperBuilder tables(String[] tables);
        public SQLiteHelperBuilder tableNames(String [] tableNames);
        public SQLiteHelperBuilder version(int version);
        public SQLiteHelperBuilder name(String name);
        public SQLiteHelperBuilder factory(SQLiteDatabase.CursorFactory factory);
    }
}
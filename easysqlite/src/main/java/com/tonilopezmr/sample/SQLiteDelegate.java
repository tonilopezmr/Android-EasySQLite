/*
 * Copyright 2015 tonilopezmr.com
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

package com.tonilopezmr.sample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Class delegated charge of implementing CRUD methods for any object model.
 *
 * @author Antonio Lopez
 */
public class SQLiteDelegate<T> implements DataAccesObject<T> {

    protected SQLiteTransformer<T> transformer;
    protected SQLiteDatabase db;

    public SQLiteDelegate(SQLiteDatabase db, SQLiteTransformer<T> transformer) {
        this.transformer = transformer;
        this.db = db;
    }

    @Override
    public synchronized T create(T dto) throws Exception {
        long rowid = db.insert(transformer.getTableName(),null,transformer.transform(dto));
        Log.i(this.getClass().getName(), "ROW ID: " + rowid);
        return transformer.setId(dto, rowid);
    }

    @Override
    public synchronized boolean update(T dto) throws Exception {
        int confirm = db.update(transformer.getTableName(), transformer.transform(dto),transformer.getWhereClause(dto), null);
        return confirm!=0;
    }

    @Override
    public synchronized boolean delete(T dto) throws Exception {
        int confirm = db.delete(transformer.getTableName(), transformer.getWhereClause(dto), null);
        return confirm!=0;
    }

    @Override
    public synchronized boolean deleteAll() throws Exception {
        int confirm = db.delete(transformer.getTableName(), null, null);
        return confirm!=0;
    }

    @Override
    public synchronized T read(T id) throws Exception {
        Cursor cursor = db.query(transformer.getTableName(), transformer.getFields(), transformer.getWhereClause(id),null, null,null,null);
        T object = null;
        if (cursor.moveToFirst()){
            object = transformer.transform(cursor);
        }
        return object;
    }

    @Override
    public synchronized Collection<T> readAll() throws Exception {
        Cursor cursor = db.query(transformer.getTableName(), transformer.getFields(), null, null, null, null, null);
        Collection<T> list = getAllCursor(cursor);
        return list;
    }

    private synchronized Collection<T> getAllCursor(Cursor c) throws Exception{
        Collection<T> list = new LinkedList<>();
        if (c.moveToFirst()){
            do {
                list.add(transformer.transform(c));
            }while (c.moveToNext());
        }
        return list;
    }
}
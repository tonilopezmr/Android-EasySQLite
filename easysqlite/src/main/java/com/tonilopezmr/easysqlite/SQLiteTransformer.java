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

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Interfaz Transformer for SQLite.
 *
 * @author Juan Vicente Carrillo
 * @author Antonio López Marín
 */
public interface SQLiteTransformer<T> {
    T transform(Cursor cursor) throws Exception;
    ContentValues transform(T dto) throws Exception;
    String getWhereClause(T dto) throws Exception;
    T setId(T dto, int id) throws Exception;
    String[] getFields() throws Exception;
    String getTableName() throws Exception;
}

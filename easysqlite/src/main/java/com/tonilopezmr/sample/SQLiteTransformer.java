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

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Antonio Lopez on 1/05/14.
 *
 * Interfaz Transformer para SQLite.
 *
 * @author Antonio López Marín
 */
public interface SQLiteTransformer<T> {
    public T transform(Cursor cursor) throws Exception;
    public ContentValues transform(T dto) throws Exception;
    public String getWhereClause(T dto) throws Exception;
    public T setId(T dto, Object id) throws Exception;
    public String[] getFields() throws Exception;
    public String getTableName() throws Exception;
}

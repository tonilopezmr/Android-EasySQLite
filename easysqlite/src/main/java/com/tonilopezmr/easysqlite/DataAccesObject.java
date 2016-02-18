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

import java.util.Collection;

/**
 * Generic Interface Data Access Object.
 *
 * @author Juan Vicente Carrillo
 * @author Antonio López Marín
 */
public interface DataAccesObject<T> {
    // CRUD
    T create(T dto) throws Exception;
    int update(T dto) throws Exception;
    T read(T dto) throws Exception;
    Collection<T> readAll() throws Exception;
    boolean delete(T dto) throws Exception;
    int deleteAll() throws Exception;
}

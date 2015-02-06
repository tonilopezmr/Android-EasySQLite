/*
 * Copyright (c) 2014 Antonio López Marín.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *   Author: Antonio López Marín
 * Internet: tonilopezmr.com
 */

package com.tonilopezmr.sample;

import java.util.Collection;

/**
 * Generic Interface Data Access Object
 *
 * @author Juan Vicente Carrillo
 * @author Antonio López Marín
 */
public interface DataAccesObject<T> {
    // CRUD
    public T create(T dto) throws Exception;
    public boolean update(T dto) throws Exception;
    public T read(T id) throws Exception;
    public Collection<T> readAll() throws Exception;
    public boolean delete(T dto) throws Exception;
    public boolean deleteAll() throws Exception;
}

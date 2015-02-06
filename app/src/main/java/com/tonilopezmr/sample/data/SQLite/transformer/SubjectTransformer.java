package com.tonilopezmr.sample.data.SQLite.transformer;

import android.content.ContentValues;
import android.database.Cursor;

import com.tonilopezmr.sample.SQLiteTransformer;
import com.tonilopezmr.sample.domain.Subject;

/**
 * Created by toni on 05/02/15.
 */
public class SubjectTransformer implements SQLiteTransformer<Subject>{

    public static final String ID = "id";
    public static final String NAME = "name";

    public static final String TABLE_NAME = "subject";
    public static final String[] FIELDS = {ID, NAME};

    @Override
    public Subject transform(Cursor cursor) throws Exception {
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        return new Subject(id, name);
    }

    @Override
    public ContentValues transform(Subject dto) throws Exception {
        ContentValues values = new ContentValues();
        values.put(ID, dto.getId());
        values.put(NAME, dto.getName());

        return values;
    }

    @Override
    public String getWhereClause(Subject dto) throws Exception {
        return  ID+"="+dto.getId();
    }

    @Override
    public Subject setId(Subject dto, Object id) throws Exception {
        dto.setId((Integer.valueOf(dto.toString())));
        return dto;
    }

    @Override
    public String[] getFields() throws Exception {
        return FIELDS;
    }

    @Override
    public String getTableName() throws Exception {
        return TABLE_NAME;
    }
}

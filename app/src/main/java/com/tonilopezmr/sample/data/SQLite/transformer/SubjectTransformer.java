package com.tonilopezmr.sample.data.SQLite.transformer;

import android.content.ContentValues;
import android.database.Cursor;

import com.tonilopezmr.easysqlite.SQLiteTransformer;
import com.tonilopezmr.sample.data.SQLite.entity.SubjectEntity;

/**
 *
 * @author Toni
 */
public class SubjectTransformer implements SQLiteTransformer<SubjectEntity>{

    public static final String ID = "id";
    public static final String NAME = "name";

    public static final String TABLE_NAME = "subject";
    public static final String[] FIELDS = {ID, NAME};

    @Override
    public SubjectEntity transform(Cursor cursor) throws Exception {
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        return new SubjectEntity(id, name);
    }

    @Override
    public ContentValues transform(SubjectEntity dto) throws Exception {
        ContentValues values = new ContentValues();
//        values.put(ID, dto.getId());   it is not necessary, autoincrement!
        values.put(NAME, dto.getName());

        return values;
    }

    @Override
    public String getWhereClause(SubjectEntity dto) throws Exception {
        return  ID+"="+dto.getId();
    }

    @Override
    public SubjectEntity setId(SubjectEntity dto, int id) throws Exception {
        dto.setId(id);
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

package com.tonilopezmr.sample.data.SQLite.dao;

import android.database.sqlite.SQLiteDatabase;

import com.tonilopezmr.sample.SQLiteDelegate;
import com.tonilopezmr.sample.data.SQLite.entity.SubjectEntity;
import com.tonilopezmr.sample.data.SQLite.transformer.SubjectTransformer;

/**
 * Created by toni on 05/02/15.
 */
public class SubjectDAO extends SQLiteDelegate<SubjectEntity> {
    public SubjectDAO(SQLiteDatabase db) {
        super(db, new SubjectTransformer());
    }
}

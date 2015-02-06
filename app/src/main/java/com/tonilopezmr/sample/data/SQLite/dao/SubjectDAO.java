package com.tonilopezmr.sample.data.SQLite.dao;

import android.database.sqlite.SQLiteDatabase;

import com.tonilopezmr.sample.SQLiteDelegate;
import com.tonilopezmr.sample.data.SQLite.transformer.SubjectTransformer;
import com.tonilopezmr.sample.domain.Subject;

/**
 * Created by toni on 05/02/15.
 */
public class SubjectDAO extends SQLiteDelegate<Subject> {
    public SubjectDAO(SQLiteDatabase db) {
        super(db, new SubjectTransformer());
    }
}

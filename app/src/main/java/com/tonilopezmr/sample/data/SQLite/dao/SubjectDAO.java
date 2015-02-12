package com.tonilopezmr.sample.data.SQLite.dao;

import android.database.sqlite.SQLiteDatabase;

import com.tonilopezmr.easysqlite.SQLiteDelegate;
import com.tonilopezmr.sample.data.SQLite.entity.SubjectEntity;
import com.tonilopezmr.sample.data.SQLite.transformer.SubjectTransformer;

/**
 * DAO implementation, important extend for SQLiteDelegate and indicate the ObjectEntity.
 * Each DAO implementation should have a Transformer.
 *
 * @author Toni
 */
public class SubjectDAO extends SQLiteDelegate<SubjectEntity> {
    public SubjectDAO(SQLiteDatabase db) {
        super(db, new SubjectTransformer());
    }
}

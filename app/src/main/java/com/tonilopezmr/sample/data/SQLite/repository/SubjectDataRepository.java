package com.tonilopezmr.sample.data.SQLite.repository;

import android.content.Context;

import com.tonilopezmr.sample.data.SQLite.SQLiteManager;
import com.tonilopezmr.sample.data.SQLite.dao.SubjectDAO;
import com.tonilopezmr.sample.domain.repository.SubjectRepository;

import java.util.Collection;
import java.util.Random;

/**
 * Created by toni on 05/02/15.
 */
public class SubjectDataRepository implements SubjectRepository {

    private SubjectDAO subjectDAO;

    public SubjectDataRepository(Context context) {
        subjectDAO = new SubjectDAO(SQLiteManager.getDataBase(context));
    }

    @Override
    public void getSubjectsCollection(SubjectCallback callback) {
        try {
            randomError();
            callback.onSubjectListLoader(subjectDAO.readAll());
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError();
        }
    }

    public void randomError(){
        Random random = new Random();

        Object[] objects = {3,4,"as",5,4,"SDF", 4, "sdf"};
        int i = Integer.valueOf(objects[random.nextInt(7+1)].toString());
    }
}

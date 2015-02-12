package com.tonilopezmr.sample.data.SQLite.repository;

import android.content.Context;

import com.tonilopezmr.sample.data.SQLite.SQLiteManager;
import com.tonilopezmr.sample.data.SQLite.dao.SubjectDAO;
import com.tonilopezmr.sample.data.SQLite.entity.SubjectEntity;
import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;
import com.tonilopezmr.sample.domain.mapper.SubjectMapper;
import com.tonilopezmr.sample.domain.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 *
 * @author Toni
 */
public class SubjectDataRepository implements SubjectRepository {

    private SubjectDAO subjectDAO;
    private SubjectMapper<SubjectEntity> mapper;

    public SubjectDataRepository(Context context, SubjectMapper mapper) {
        subjectDAO = new SubjectDAO(SQLiteManager.getDataBase(context));
        this.mapper = mapper;
    }

    @Override
    public void getSubjectsCollection(SubjectListCallback callback) throws SubjectException{
        try {
            randomError();
            callback.onSubjectListLoader(createCollecitonSubject(subjectDAO.readAll()));
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(new SubjectException(e));
        }
    }

    @Override
    public void createSubject(Subject subject, SubjectUseCase callback) throws SubjectException {
        try {
            SubjectEntity subjectEntity = mapper.mapToSubjectEntity(subject);
            callback.onMissionAccomplished(mapper.mapToSubject(subjectDAO.create(subjectEntity)));
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(new SubjectException(e));
        }
    }

    @Override
    public void deleteSubject(Subject subject, SubjectUseCase callback) throws SubjectException {
        try {
            SubjectEntity subjectEntity = mapper.mapToSubjectEntity(subject);
            boolean isDelete = subjectDAO.delete(subjectEntity);
            if (isDelete){
                callback.onMissionAccomplished(subject);
            }else{
                callback.onError(new SubjectException("Has not been deleted"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(new SubjectException(e));
        }
    }

    private Collection<Subject> createCollecitonSubject(Collection<SubjectEntity> entityCollection){
        Collection<Subject> subjects = new ArrayList<>();

        for (SubjectEntity subjectEntity : entityCollection){
            subjects.add(mapper.mapToSubject(subjectEntity));
        }

        return subjects;
    }

    public void randomError(){
        Random random = new Random();

        Object[] objects = {3,4,"Oops :(",5,4,"Error :D", 4, 8};
        int i = Integer.valueOf(objects[random.nextInt(7+1)].toString());
    }
}

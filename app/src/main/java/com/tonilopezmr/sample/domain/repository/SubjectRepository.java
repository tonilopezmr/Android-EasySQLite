package com.tonilopezmr.sample.domain.repository;

import com.tonilopezmr.sample.domain.Subject;
import java.util.Collection;

/**
 * Created by toni on 04/02/15.
 */
public interface SubjectRepository {

    interface SubjectCallback{
        void onSubjectListLoader(Collection<Subject> subjects);
        void onError();
    }

    void getSubjectsCollection(SubjectCallback subjectCallback);
}

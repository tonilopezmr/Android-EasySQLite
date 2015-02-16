package com.tonilopezmr.sample.domain.repository;

import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;

import java.util.Collection;

/**
 * @author toni.
 */
public interface SubjectRepository {

    interface SubjectListCallback {
        void onSubjectListLoader(Collection<Subject> subjects);
        void onError(SubjectException subjectException);
    }

    interface SubjectUseCase {
        void onMissionAccomplished(Subject subject);
        void onError(SubjectException subjectException);
    }

    void getSubjectsCollection(SubjectListCallback callback) throws SubjectException;
    void createSubject(Subject subject, SubjectUseCase callback) throws SubjectException;
    void deleteSubject(Subject subject, SubjectUseCase callback) throws  SubjectException;
}

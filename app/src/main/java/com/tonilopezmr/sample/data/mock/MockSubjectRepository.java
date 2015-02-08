package com.tonilopezmr.sample.data.mock;

import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;
import com.tonilopezmr.sample.domain.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Mock repository for testing.
 *
 * @author Toni
 */
public class MockSubjectRepository  implements SubjectRepository {

    private Collection<Subject> generateSubjectCollection() {

        Collection<Subject> subjects = new ArrayList<>();

        subjects.add(createSubject(1,"Fisica"));
        subjects.add(createSubject(2, "Algebra"));
        subjects.add(createSubject(3, "Estadistica"));
        subjects.add(createSubject(4, "Analisis Matematico"));
        subjects.add(createSubject(5, "Iniciación a la programación"));
        subjects.add(createSubject(6, "Fundamentos de los computadores"));
        subjects.add(createSubject(7, "Empresa"));

        return subjects;
    }

    private Subject createSubject(int id, String name) {
        return new Subject(id, name);
    }


    @Override
    public void getSubjectsCollection(SubjectListCallback subjectListCallback){
        subjectListCallback.onSubjectListLoader(generateSubjectCollection());
    }

    @Override
    public void createSubject(Subject subject, SubjectUseCase callback) throws SubjectException {
        subject.setId(10);
        subject.setName("Create new Subject");
        callback.onMissionAccomplished(subject);
    }

    @Override
    public void deleteSubject(Subject subject, SubjectUseCase callback) throws SubjectException {
        callback.onMissionAccomplished(subject);
    }
}

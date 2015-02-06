package com.tonilopezmr.sample.data.mock;

import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by toni on 04/02/15.
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
    public void getSubjectsCollection(SubjectCallback subjectCallback){
        subjectCallback.onSubjectListLoader(generateSubjectCollection());
    }
}

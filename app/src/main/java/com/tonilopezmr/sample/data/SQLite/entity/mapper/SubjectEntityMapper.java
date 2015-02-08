package com.tonilopezmr.sample.data.SQLite.entity.mapper;

import com.tonilopezmr.sample.data.SQLite.entity.SubjectEntity;
import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.mapper.SubjectMapper;

import javax.inject.Inject;

/**
 *
 * @author Toni
 */
public class SubjectEntityMapper implements SubjectMapper<SubjectEntity>{

    public SubjectEntityMapper() {
    }

    public Subject mapToSubject(SubjectEntity subjectEntity){
        return new Subject(subjectEntity.getId(), subjectEntity.getName());
    }

    public SubjectEntity mapToSubjectEntity(Subject subject){
        return new SubjectEntity(subject.getId(), subject.getName());
    }
}

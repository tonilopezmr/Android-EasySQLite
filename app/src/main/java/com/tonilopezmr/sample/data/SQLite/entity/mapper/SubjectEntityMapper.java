package com.tonilopezmr.sample.data.SQLite.entity.mapper;

import com.tonilopezmr.sample.data.SQLite.entity.SubjectEntity;
import com.tonilopezmr.sample.domain.Subject;

/**
 * Created by toni on 06/02/15.
 */
public class SubjectEntityMapper {

    public SubjectEntityMapper() {
    }

    public Subject mapToSubject(SubjectEntity subjectEntity){
        return new Subject(subjectEntity.getId(), subjectEntity.getName());
    }

    public SubjectEntity mapToSubjectEntity(Subject subject){
        return new SubjectEntity(subject.getId(), subject.getName());
    }
}

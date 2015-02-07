package com.tonilopezmr.sample.domain.mapper;

import com.tonilopezmr.sample.domain.Subject;

/**
 * Created by toni on 07/02/15.
 */
public interface SubjectMapper<T> {

    public Subject mapToSubject(T subjectEntity);

    public T mapToSubjectEntity(Subject subject);
}

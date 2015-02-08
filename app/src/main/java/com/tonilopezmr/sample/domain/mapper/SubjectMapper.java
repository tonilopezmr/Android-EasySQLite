package com.tonilopezmr.sample.domain.mapper;

import com.tonilopezmr.sample.domain.Subject;

/**
 * @author toni.
 */
public interface SubjectMapper<T> {

    public Subject mapToSubject(T subjectEntity);

    public T mapToSubjectEntity(Subject subject);
}

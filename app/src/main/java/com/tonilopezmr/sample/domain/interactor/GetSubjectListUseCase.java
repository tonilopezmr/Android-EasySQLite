package com.tonilopezmr.sample.domain.interactor;

import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;

import java.util.Collection;

/**
 * @author toni.
 */
public interface GetSubjectListUseCase extends Interactor {

    interface Callback{
        void onSubjectListLoaded(Collection<Subject> subjects);
        void onError(SubjectException exception);
    }

    public void execute(final Callback callback);
}

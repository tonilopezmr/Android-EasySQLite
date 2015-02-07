package com.tonilopezmr.sample.domain.interactor;

import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;

/**
 * Created by toni on 07/02/15.
 */
public interface CreateSubjectUseCase extends Interactor {

    interface Callback {
        void onCreateSubject(Subject subject);
        void onError(SubjectException ex);
    }

    public void execute(Subject newSubject, final Callback callback);
}

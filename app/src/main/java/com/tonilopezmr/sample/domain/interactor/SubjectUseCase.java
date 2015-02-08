package com.tonilopezmr.sample.domain.interactor;

import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;

/**
 * @author toni.
 */
public interface SubjectUseCase extends Interactor {

    interface Callback {
        void onMissionAccomplished(Subject subject);
        void onError(SubjectException ex);
    }

    public void execute(Subject subject, final Callback callback);
}

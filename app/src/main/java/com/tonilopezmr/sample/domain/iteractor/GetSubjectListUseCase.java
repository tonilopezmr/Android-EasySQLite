package com.tonilopezmr.sample.domain.iteractor;

import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;

import java.util.Collection;

/**
 * Created by toni on 03/02/15.
 */
public interface GetSubjectListUseCase extends Interactor {

    interface Callback{
        void onSubjectListLoaded(Collection<Subject> subjects);
        void onError(SubjectException exception);
    }

    public void execute(final Callback callback);
}

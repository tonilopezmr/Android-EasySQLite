package com.tonilopezmr.sample.executor;

import com.tonilopezmr.sample.domain.interactor.Interactor;

/**
 * Created by toni on 05/02/15.
 */
public interface Executor {
    void run(final Interactor interactor);
}

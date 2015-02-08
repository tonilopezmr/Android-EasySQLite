package com.tonilopezmr.sample.executor;

import com.tonilopezmr.sample.domain.interactor.Interactor;

/**
 * @author toni.
 */
public interface Executor {
    void run(final Interactor interactor);
}

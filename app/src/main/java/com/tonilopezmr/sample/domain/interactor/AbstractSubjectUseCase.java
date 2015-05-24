package com.tonilopezmr.sample.domain.interactor;

import com.tonilopezmr.sample.domain.repository.SubjectRepository;
import com.tonilopezmr.sample.executor.Executor;
import com.tonilopezmr.sample.executor.MainThread;

/**
 * @author toni.
 */
public abstract class AbstractSubjectUseCase implements SubjectUseCase {

    protected Executor executor;
    protected MainThread mainThread;
    protected SubjectRepository subjectRepository;

    public AbstractSubjectUseCase(Executor executor, MainThread mainThread, SubjectRepository subjectRepository) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.subjectRepository = subjectRepository;
    }

}

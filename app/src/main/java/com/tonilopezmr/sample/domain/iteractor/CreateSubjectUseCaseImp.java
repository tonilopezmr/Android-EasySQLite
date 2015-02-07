package com.tonilopezmr.sample.domain.iteractor;

import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;
import com.tonilopezmr.sample.domain.repository.SubjectRepository;
import com.tonilopezmr.sample.executor.Executor;
import com.tonilopezmr.sample.executor.MainThread;

/**
 * Created by toni on 07/02/15.
 */
public class CreateSubjectUseCaseImp implements CreateSubjectUseCase {

    private Executor executor;
    private MainThread mainThread;
    private SubjectRepository subjectRepository;

    private Callback callback;
    private Subject subject;

    public CreateSubjectUseCaseImp(Executor executor, MainThread mainThread, SubjectRepository subjectRepository) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void execute(Subject subject, final Callback callback) {
        if (callback == null){
            throw new IllegalArgumentException("Callback parameter can't be null");
        }

        this.callback = callback;
        this.subject = subject;
        this.executor.run(this);
    }


    //Interactor Use case
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        subjectRepository.createSubject(subject, new SubjectRepository.SubjectCreateCallback() {
            @Override
            public void onCreateSubject(final Subject subject) {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCreateSubject(subject);
                    }
                });
            }

            @Override
            public void onError(final SubjectException subjectException) {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(subjectException);
                    }
                });
            }
        });
    }
}

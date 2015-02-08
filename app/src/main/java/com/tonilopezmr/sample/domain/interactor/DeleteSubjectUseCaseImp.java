package com.tonilopezmr.sample.domain.interactor;

import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;
import com.tonilopezmr.sample.domain.repository.SubjectRepository;
import com.tonilopezmr.sample.executor.Executor;
import com.tonilopezmr.sample.executor.MainThread;

/**
 * @author toni.
 */
public class DeleteSubjectUseCaseImp extends AbstractSubjectUseCase implements SubjectUseCase {

    private Callback callback;
    private Subject subject;

    public DeleteSubjectUseCaseImp(Executor executor, MainThread mainThread, SubjectRepository subjectRepository) {
        super(executor, mainThread, subjectRepository);
    }

    @Override
    public void execute(Subject subject, final Callback callback) {
        if (callback == null){
            throw new IllegalArgumentException("Callback parameter can't be null");
        }

        this.callback = callback;
        this.subject = subject;
        getExecutor().run(this);
    }


    //Interactor Use case
    @Override
    public void run() {
        getSubjectRepository().deleteSubject(subject, new SubjectRepository.SubjectUseCase() {
            @Override
            public void onMissionAccomplished(final Subject subject) {
                getMainThread().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMissionAccomplished(subject);
                    }
                });
            }

            @Override
            public void onError(final SubjectException subjectException) {
                getMainThread().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(subjectException);
                    }
                });
            }
        });
    }
}

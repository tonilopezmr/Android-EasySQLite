package com.tonilopezmr.sample.domain.iteractor;

import android.os.Handler;
import android.util.Log;

import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;
import com.tonilopezmr.sample.domain.repository.SubjectRepository;
import com.tonilopezmr.sample.executor.Executor;
import com.tonilopezmr.sample.executor.MainThread;

import java.util.Collection;


/**
 * Created by toni on 03/02/15.
 */
public class GetSubjectListUseCaseImp implements GetSubjectListUseCase{


    private SubjectRepository subjectRepository;
    private Executor executor;
    private MainThread mainThread;

    private Callback callback;

    public GetSubjectListUseCaseImp(Executor executor, MainThread mainThread, SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
        this.executor = executor;
        this.mainThread = mainThread;
    }

    //aqui puede tirarse el rato que quiera porque esta en un hilo
    //De la implementacion de GetSubjectListUseCase
    @Override
    public void execute(final Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback parameter can't be null");
        }

        this.callback = callback;
        this.executor.run(this);
    }

    //Interactor
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        subjectRepository.getSubjectsCollection(new SubjectRepository.SubjectCallback() {
            @Override
            public void onSubjectListLoader(final Collection<Subject> subjects) {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSubjectListLoaded(subjects);
                    }
                });
            }

            @Override
            public void onError(final SubjectException exception) {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(exception);
                        Log.i(getClass().toString(), "Error!");
                    }
                });
            }
        });
    }

}

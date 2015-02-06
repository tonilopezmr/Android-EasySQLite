package com.tonilopezmr.sample.ui.presenter;

import android.util.Log;

import com.tonilopezmr.sample.data.SQLite.repository.SubjectDataRepository;
import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;
import com.tonilopezmr.sample.domain.iteractor.GetSubjectListUseCase;
import com.tonilopezmr.sample.domain.iteractor.GetSubjectListUseCaseImp;
import com.tonilopezmr.sample.executor.MainThread;
import com.tonilopezmr.sample.executor.MainThreadImp;
import com.tonilopezmr.sample.executor.ThreadExecutor;
import com.tonilopezmr.sample.ui.view.View;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModelImp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by toni on 04/02/15.
 */
public class SubjectListPresenter implements Presenter {

    private View view;
    private GetSubjectListUseCase subjectListUseCase;

    public SubjectListPresenter(View view) {
        this.view = view;

        //with dragger
        ThreadExecutor executor = new ThreadExecutor();
        MainThread mainThread = new MainThreadImp();

        this.subjectListUseCase = new GetSubjectListUseCaseImp(executor, mainThread, new SubjectDataRepository(view.getContext()));
    }

    @Override
    public void onInit() {
        view.showProgress();
        subjectListUseCase.execute(new GetSubjectListUseCase.Callback() {
            @Override
            public void onSubjectListLoaded(Collection<Subject> subjects) {
                view.showSubjects(convertToViewModel(subjects));
                view.hideProgress();
            }

            @Override
            public void onError(SubjectException exception) {
                view.showMessage(exception.getMessage()); //For example
                view.hideProgress();
                view.showError();
                Log.i(getClass().toString(), "¡¡Show error!!");
            }
        });
    }

    private Collection<SubjectViewModel> convertToViewModel(Collection<Subject> subjectsCollection){
        Collection<SubjectViewModel> subjectsViewModel = new ArrayList<>();

        for (Subject subject : subjectsCollection){
            subjectsViewModel.add(new SubjectViewModelImp(subject));
        }

        return subjectsViewModel;
    }

    @Override
    public void onClickItem(String name) {
        view.showMessage("the subject: "+name);
    }

    @Override
    public void onViewDestroy() {

    }
}

package com.tonilopezmr.sample.ui.presenter;

import android.util.Log;

import com.tonilopezmr.sample.data.SQLite.repository.SubjectDataRepository;
import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;
import com.tonilopezmr.sample.domain.iteractor.CreateSubjectUseCase;
import com.tonilopezmr.sample.domain.iteractor.CreateSubjectUseCaseImp;
import com.tonilopezmr.sample.domain.iteractor.GetSubjectListUseCase;
import com.tonilopezmr.sample.domain.iteractor.GetSubjectListUseCaseImp;
import com.tonilopezmr.sample.executor.MainThread;
import com.tonilopezmr.sample.executor.MainThreadImp;
import com.tonilopezmr.sample.executor.ThreadExecutor;
import com.tonilopezmr.sample.ui.view.SubjectListView;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModelImp;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by toni on 04/02/15.
 */
public class SubjectListPresenterImp implements MainPresenter {

    private SubjectListView view;
    private GetSubjectListUseCase subjectListUseCase;
    private CreateSubjectUseCase createSubjectUseCase;

    public SubjectListPresenterImp(SubjectListView view) {
        this.view = view;

        //with dragger
        ThreadExecutor executor = new ThreadExecutor();
        MainThread mainThread = new MainThreadImp();

        SubjectDataRepository repository = new SubjectDataRepository(view.getContext());

        this.subjectListUseCase = new GetSubjectListUseCaseImp(executor, mainThread, repository);
        this.createSubjectUseCase = new CreateSubjectUseCaseImp(executor, mainThread, repository);
    }

    @Override
    public void onInit() {
        view.showProgress();
        showSubjects();
    }

    private void showSubjects(){
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
                view.showLayoutError();
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

    private void hideLayoutError(){
        if (view.isShowLayoutError()){
            onInit();
            view.hideLayoutError();
        }
    }

    @Override
    public void onFloatingButtonClick(final SubjectViewModel subjectModel) {
        createSubjectUseCase.execute(new Subject(subjectModel.getName()), new CreateSubjectUseCase.Callback() {
            @Override
            public void onCreateSubject(Subject subject) {
                hideLayoutError();
                view.add(new SubjectViewModelImp(subject));
                view.showMessage("Create new subject number "+subject.getId());
            }

            @Override
            public void onError(SubjectException ex) {
                view.showMessage(ex.getMessage());
            }
        });
    }

    @Override
    public void onViewDestroy() {

    }
}

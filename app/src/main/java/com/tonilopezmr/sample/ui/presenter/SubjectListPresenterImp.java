package com.tonilopezmr.sample.ui.presenter;

import android.content.Context;
import android.util.Log;

import com.tonilopezmr.sample.di.BasePresenter;
import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.domain.exception.SubjectException;
import com.tonilopezmr.sample.domain.interactor.SubjectUseCase;
import com.tonilopezmr.sample.domain.interactor.GetSubjectListUseCase;
import com.tonilopezmr.sample.ui.view.SubjectListView;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModelImp;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author toni.
 */
public class SubjectListPresenterImp extends BasePresenter implements SubjectListPresenter {

    private SubjectListView view;

    private GetSubjectListUseCase subjectListUseCase;
    private SubjectUseCase createSubjectUseCase;
    private SubjectUseCase deleteSubjectUseCase;

    public SubjectListPresenterImp(Context context, GetSubjectListUseCase subjectListUseCase,
                                   SubjectUseCase createSubjectUseCase,
                                   @Named("delete usecase") SubjectUseCase deleteSubjectUseCase) {
        super(context);
        this.createSubjectUseCase = createSubjectUseCase;
        this.deleteSubjectUseCase = deleteSubjectUseCase;
        this.subjectListUseCase = subjectListUseCase;
    }

    @Override
    public void setView(SubjectListView view){
        this.view = view;
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
    public void onClickItem(SubjectViewModel subjectModel) {
        view.showMessage("the subject: "+subjectModel);
    }

    @Override
    public void onLongItemClick(SubjectViewModel subjectModel) {
        deleteSubjectUseCase.execute(new Subject(subjectModel.getId(), subjectModel.getName()), new SubjectUseCase.Callback() {
            @Override
            public void onMissionAccomplished(Subject subject) {
                view.remove(new SubjectViewModelImp(subject));
                view.showMessage("Mission accomplished, "+subject+", it has been deleted.");
            }

            @Override
            public void onError(SubjectException ex) {
                view.showMessage(ex.getMessage());
            }
        });
    }

    @Override
    public void onRetryButtonClick() {
        view.hideLayoutError();
        onInit();
    }

    @Override
    public void onFloatingButtonClick(final SubjectViewModel subjectModel) {
        createSubjectUseCase.execute(new Subject(subjectModel.getName()), new SubjectUseCase.Callback() {
            @Override
            public void onMissionAccomplished(Subject subject) {
                view.add(new SubjectViewModelImp(subject));
                view.showMessage("Create new subject number "+subject.getId());
            }

            @Override
            public void onError(SubjectException ex) {
                view.showMessage(ex.getMessage());
            }
        });
    }

}

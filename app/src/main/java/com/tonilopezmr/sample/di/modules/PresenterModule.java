package com.tonilopezmr.sample.di.modules;

import android.content.Context;

import com.tonilopezmr.sample.domain.interactor.GetSubjectListUseCase;
import com.tonilopezmr.sample.domain.interactor.SubjectUseCase;
import com.tonilopezmr.sample.ui.presenter.SubjectListPresenter;
import com.tonilopezmr.sample.ui.presenter.SubjectListPresenterImp;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Antonio López.
 */
@Module(
        complete = false,
        library = true
)
public class PresenterModule {

    @Provides
    public SubjectListPresenter provideMainPresenter(Context context, GetSubjectListUseCase subjectListUseCase,
                                              @Named("create usecase") SubjectUseCase createSubjectUseCase,
                                              @Named("delete usecase") SubjectUseCase deleteSubjectUseCase){
        return new SubjectListPresenterImp(context, subjectListUseCase, createSubjectUseCase, deleteSubjectUseCase);
    }
}

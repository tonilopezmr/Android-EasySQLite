package com.tonilopezmr.sample.di.modules;

import com.tonilopezmr.sample.domain.interactor.CreateSubjectUseCase;
import com.tonilopezmr.sample.domain.interactor.CreateSubjectUseCaseImp;
import com.tonilopezmr.sample.domain.interactor.GetSubjectListUseCase;
import com.tonilopezmr.sample.domain.interactor.GetSubjectListUseCaseImp;
import com.tonilopezmr.sample.domain.repository.SubjectRepository;
import com.tonilopezmr.sample.executor.Executor;
import com.tonilopezmr.sample.executor.MainThread;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by toni on 07/02/15.
 */
@Module(
        complete = false,
        library = true
)
public final class InteractorModule {

    @Provides
    public CreateSubjectUseCase provideCreateSubjectUseCase(Executor executor, MainThread mainThread, @Named("internal_database")SubjectRepository subjectRepository){
        return new CreateSubjectUseCaseImp(executor, mainThread, subjectRepository);
    }

    @Provides
    public GetSubjectListUseCase provideGetSubjectListUseCase(Executor executor, MainThread mainThread, @Named("internal_database")SubjectRepository subjectRepository){
        return new GetSubjectListUseCaseImp(executor, mainThread, subjectRepository);
    }
}

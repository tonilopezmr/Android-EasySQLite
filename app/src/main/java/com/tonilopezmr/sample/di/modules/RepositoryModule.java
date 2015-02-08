package com.tonilopezmr.sample.di.modules;

import android.content.Context;

import com.tonilopezmr.sample.data.SQLite.entity.mapper.SubjectEntityMapper;
import com.tonilopezmr.sample.data.SQLite.repository.SubjectDataRepository;
import com.tonilopezmr.sample.data.mock.MockSubjectRepository;
import com.tonilopezmr.sample.domain.mapper.SubjectMapper;
import com.tonilopezmr.sample.domain.repository.SubjectRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 *
 * @author Toni
 */
@Module(
        complete = false,
        library = true
)
public final class RepositoryModule {

    @Provides
    public SubjectMapper provideSubjectMapper(){
        return new SubjectEntityMapper();
    }

    @Provides
    @Named("internal_database")
    public SubjectRepository provideSubjectRepository(Context context, SubjectMapper mapper){
        return new SubjectDataRepository(context, mapper);
    }

    @Provides
    @Named("mock_database")
    public SubjectRepository provideSubjectRepository(){
        return new MockSubjectRepository();
    }
}

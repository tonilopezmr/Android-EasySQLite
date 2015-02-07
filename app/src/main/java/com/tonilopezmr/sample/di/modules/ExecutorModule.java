package com.tonilopezmr.sample.di.modules;

import com.tonilopezmr.sample.executor.Executor;
import com.tonilopezmr.sample.executor.MainThread;
import com.tonilopezmr.sample.executor.MainThreadImp;
import com.tonilopezmr.sample.executor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by toni on 07/02/15.
 */
@Module(
        complete = false,
        library = true
)
public final class ExecutorModule {

    @Provides
    @Singleton
    public Executor provideThreadExecutor(){
        return new ThreadExecutor();
    }

    @Provides
    @Singleton
    public MainThread provideMainThreadImp(){
        return new MainThreadImp();
    }

}

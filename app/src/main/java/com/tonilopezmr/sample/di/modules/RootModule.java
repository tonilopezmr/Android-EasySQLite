package com.tonilopezmr.sample.di.modules;

import android.content.Context;
import android.view.LayoutInflater;

import com.tonilopezmr.sample.di.BaseActivity;
import com.tonilopezmr.sample.di.SubjectsApplication;
import com.tonilopezmr.sample.ui.MainActivity;
import com.tonilopezmr.sample.ui.presenter.SubjectListPresenterImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by toni on 07/02/15.
 */
@Module(
        includes = {
            ExecutorModule.class,
            InteractorModule.class,
            RepositoryModule.class
        },
        injects = {
                SubjectsApplication.class,
                SubjectListPresenterImp.class,
                MainActivity.class
        },
        library = true
)
public final class RootModule {

    private final Context context;

    public RootModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext(){
        return this.context;
    }

    @Provides
    public LayoutInflater provideLayoutInflater(){
        return LayoutInflater.from(this.context);
    }
}

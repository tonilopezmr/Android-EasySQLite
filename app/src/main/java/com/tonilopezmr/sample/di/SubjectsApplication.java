package com.tonilopezmr.sample.di;

import android.app.Application;

import com.tonilopezmr.sample.di.modules.RootModule;

import java.util.List;

import dagger.ObjectGraph;

/**
 * @author toni.
 */
public class SubjectsApplication extends Application{

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencyInjector();
    }

    private void initializeDependencyInjector() {
        objectGraph = ObjectGraph.create(new RootModule(this));
        objectGraph.inject(this);
    }

    public void inject(Object object){
        objectGraph.inject(object);
    }


    public ObjectGraph plus(List<Object> modules){
        if (modules == null) {
            throw new IllegalArgumentException(
                    "You can't plus a null module, review your getModules() implementation");
        }
        return objectGraph.plus(modules.toArray());
    }
}

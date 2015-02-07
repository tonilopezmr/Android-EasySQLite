package com.tonilopezmr.sample.di;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * Created by toni on 07/02/15.
 */
public abstract class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
//        injectViews();  TODO no funciona
    }

    private void injectDependencies() {
        SubjectsApplication application = (SubjectsApplication)getApplication();
        application.inject(this);
    }

    private void injectViews(){
        ButterKnife.inject(this);
    }
}

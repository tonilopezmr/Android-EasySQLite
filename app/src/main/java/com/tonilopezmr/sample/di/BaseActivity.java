package com.tonilopezmr.sample.di;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * @author toni.
 */
public abstract class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
//        injectViews();  //TODO no funciona en BaseActivity
    }

    private void injectDependencies() {
        SubjectsApplication application = (SubjectsApplication)getApplication();
        application.inject(this);
    }

    private void injectViews(){
        ButterKnife.inject(this);
    }
}

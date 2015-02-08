package com.tonilopezmr.sample.di;

import android.content.Context;

/**
 * @author toni.
 */
public class BasePresenter {

    public BasePresenter(Context context) {
        ((SubjectsApplication)context.getApplicationContext()).inject(this);
    }
}

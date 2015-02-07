package com.tonilopezmr.sample.di;

import android.content.Context;

/**
 * Created by toni on 07/02/15.
 */
public class BasePresenter {

    public BasePresenter(Context context) {
        ((SubjectsApplication)context.getApplicationContext()).inject(this);
    }
}

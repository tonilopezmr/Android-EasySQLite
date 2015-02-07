package com.tonilopezmr.sample.executor;

import android.os.Handler;
import android.os.Looper;

import javax.inject.Inject;

/**
 * Created by toni on 03/02/15.
 */
public class MainThreadImp implements MainThread{

    private Handler handler;

    public MainThreadImp() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(final Runnable runnable) {
        handler.post(runnable);
    }
}

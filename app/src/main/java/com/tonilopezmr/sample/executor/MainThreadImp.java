package com.tonilopezmr.sample.executor;

import android.os.Handler;
import android.os.Looper;

/**
 * @author toni.
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

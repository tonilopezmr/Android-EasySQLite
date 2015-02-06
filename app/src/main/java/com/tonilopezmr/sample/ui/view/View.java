package com.tonilopezmr.sample.ui.view;

import android.content.Context;

import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;

import java.util.Collection;

/**
 * Created by toni on 04/02/15.
 */
public interface View {

    public void showError();

    public void showProgress();

    public void hideProgress();

    public void showMessage(String message);

    public void showSubjects(Collection<SubjectViewModel> subjects);

    public Context getContext();
}

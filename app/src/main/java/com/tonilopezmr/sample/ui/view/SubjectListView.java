package com.tonilopezmr.sample.ui.view;

import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;

import java.util.Collection;

/**
 * Created by toni on 07/02/15.
 */
public interface SubjectListView extends ModelListView {

    public boolean isShowLayoutError();

    public void hideLayoutError();

    public void showLayoutError();

    public void showMessage(String message);

    public void showSubjects(Collection<SubjectViewModel> subjects);

    public void showProgress();

    public void hideProgress();
}

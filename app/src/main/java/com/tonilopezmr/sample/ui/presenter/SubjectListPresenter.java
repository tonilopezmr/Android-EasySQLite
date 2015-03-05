package com.tonilopezmr.sample.ui.presenter;

import com.tonilopezmr.sample.ui.view.SubjectListView;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;

/**
 * @author toni.
 */
public interface SubjectListPresenter extends Presenter{

    void setView(SubjectListView view);

    public void onClickItem(SubjectViewModel SubjectModel);
    public void onLongItemClick(SubjectViewModel subjectModel);
    public void onRetryButtonClick();

    public void onFloatingButtonClick(SubjectViewModel subjectViewModel);
}

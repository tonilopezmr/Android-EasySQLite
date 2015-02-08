package com.tonilopezmr.sample.ui.presenter;

import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;

/**
 * @author toni.
 */
public interface SubjectListPresenter extends Presenter{

    public void onClickItem(SubjectViewModel SubjectModel);
    public void onLongItemClick(SubjectViewModel subjectModel);
}

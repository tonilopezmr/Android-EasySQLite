package com.tonilopezmr.sample.ui.view;

import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;

import java.util.Collection;
/**
 * Created by toni on 07/02/15.
 */
public interface ModelListView extends View {

    void add(SubjectViewModel subjectModel);
    void remove(SubjectViewModel subjectModel);
}

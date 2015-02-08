package com.tonilopezmr.sample.ui.view;

import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;

/**
 * @author toni.
 */
public interface ModelListView extends View {

    void add(SubjectViewModel subjectModel);
    void remove(SubjectViewModel subjectModel);
}

package com.tonilopezmr.sample.ui.viewmodel;

import com.tonilopezmr.sample.domain.Subject;

/**
 * Created by toni on 05/02/15.
 */
public class SubjectViewModelImp implements SubjectViewModel {

    Subject subject;

    public SubjectViewModelImp(Subject subject) {
        this.subject = subject;
    }

    public int getId(){
        return subject.getId();
    }

    public String getName(){
        return subject.getName();
    }

}

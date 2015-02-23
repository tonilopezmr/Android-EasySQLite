package com.tonilopezmr.sample.ui.viewmodel;

import com.tonilopezmr.sample.domain.Subject;

/**
 * Created by toni on 05/02/15.
 */
public class SubjectViewModelImp implements SubjectViewModel {

    private Subject subject;

    public SubjectViewModelImp(String name) {
        subject = new Subject(name);
    }

    public SubjectViewModelImp(Subject subject) {
        this.subject = subject;
    }

    public int getId(){
        return this.subject.getId();
    }

    public String getName(){
        return this.subject.getName();
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof SubjectViewModel){
            SubjectViewModel model = (SubjectViewModel)o;
            if (this.subject.getId() == model.getId() && this.subject.getName().equals(model.getName())){
                result = true;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return subject.getId()+" "+subject.getName();
    }
}

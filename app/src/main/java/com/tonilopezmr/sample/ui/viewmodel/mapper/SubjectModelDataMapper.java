package com.tonilopezmr.sample.ui.viewmodel.mapper;

import com.tonilopezmr.sample.domain.Subject;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModelImp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by toni on 05/02/15.
 */
public class SubjectModelDataMapper {

    public SubjectModelDataMapper() {
    }

    public SubjectViewModel transform(Subject subject){
        return new SubjectViewModelImp(subject);
    }

    public Collection<SubjectViewModel> transform(Collection<Subject> subCollection){

        List<SubjectViewModel> subjectsViewModel = new ArrayList<>();

        for (Subject subject : subCollection){
            subjectsViewModel.add(transform(subject));
        }

        return subjectsViewModel;
    }

}

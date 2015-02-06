package com.tonilopezmr.sample.domain.exception;

/**
 * Created by toni on 06/02/15.
 */
public class SubjectException extends RuntimeException {

    public SubjectException(Exception exception) {
        this.setStackTrace(exception.getStackTrace());
    }


}

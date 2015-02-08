package com.tonilopezmr.sample.domain.exception;

/**
 * @author toni.
 */
public class SubjectException extends RuntimeException {

    private String message;

    public SubjectException(String detailMessage) {
        super(detailMessage);
        this.message = detailMessage;
    }

    public SubjectException(Exception exception) {
        this.setStackTrace(exception.getStackTrace());
        this.message = exception.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}

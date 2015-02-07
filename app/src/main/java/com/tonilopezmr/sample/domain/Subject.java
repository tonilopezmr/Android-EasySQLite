package com.tonilopezmr.sample.domain;

import java.util.Collection;

/**
 * Created by toni on 03/02/15.
 */
public class Subject {

    private int id;
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: "+this.id+" Subject: "+this.name;
    }
}

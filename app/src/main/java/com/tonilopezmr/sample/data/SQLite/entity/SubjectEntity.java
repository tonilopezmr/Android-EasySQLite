package com.tonilopezmr.sample.data.SQLite.entity;

/**
 *
 * @author Toni
 */
public class SubjectEntity {
    int id;
    String name;

    public SubjectEntity(int id, String name) {
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
}

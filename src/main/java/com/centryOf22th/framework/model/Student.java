package com.centryOf22th.framework.model;

import com.centryOf22th.framework.orm.model.PrimaryKey;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by louis on 16-10-31.
 */

@Table(schema = "student")
public class Student {

    private int id;
    private String studentName;


    @Column(name = "student_name")
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


    @Column(name="id")
    @PrimaryKey
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

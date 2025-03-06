package com.capstone.RFID_padlock.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    private Long id;
    private String name;
    private int grade;
    private Long keyCardId;

    public Student() {
    }

    public Student(Long id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Long getKeyCardId() {
        return keyCardId;
    }

    public void setKeyCardId(Long keyCardId) {
        this.keyCardId = keyCardId;
    }


}

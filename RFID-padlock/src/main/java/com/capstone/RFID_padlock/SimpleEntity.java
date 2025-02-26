package com.capstone.RFID_padlock;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SimpleEntity {

    @Id
    private Long id;
    private int test;

    public SimpleEntity() {}

    public SimpleEntity(Long id, int test) {
        this.id = id;
        this.test = test;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "SimpleEntity{" + "id=" + id + ", test=" + test + '}';
    }
}

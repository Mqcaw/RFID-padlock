package com.capstone.RFID_padlock.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "locks")
public class Lock {

    @Id
    private Long id;
    private int lockerNumber;

    public Lock() {}

    public Lock(Long id, int lockerNumber) {
        this.id = id;
        this.lockerNumber = lockerNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLockerNumber() {
        return lockerNumber;
    }

    public void setLockerNumber(int lockerNumber) {
        this.lockerNumber = lockerNumber;
    }

    @Override
    public String toString() {
        return "Lock{" + "id=" + id + ", lockerNumber=" + lockerNumber + '}';
    }
}

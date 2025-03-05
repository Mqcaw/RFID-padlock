package com.capstone.RFID_padlock.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "locks")
public class Lock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int lockerNumber;
    private Long keyCardId;

    public Lock() {}

    public Lock(int lockerNumber) {
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

    public Long getKeyCardId() {
        return keyCardId;
    }

    public void setKeyCardId(Long keyCardId) {
        this.keyCardId = keyCardId;
    }

    public Lock updateData(Lock lock) {
        if (lock == null) return null;

        this.lockerNumber = lock.getLockerNumber();
        this.keyCardId = lock.getKeyCardId();
        return this;
    }

    @Override
    public String toString() {
        return "Lock{" + "id=" + id + ", lockerNumber=" + lockerNumber + '}';
    }
}

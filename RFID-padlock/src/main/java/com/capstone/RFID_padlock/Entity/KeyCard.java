package com.capstone.RFID_padlock.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="key_cards")
public class KeyCard {

    @Id
    private Long id;
    private Lock lock;

    public KeyCard() {
    }

    public KeyCard(Long id, Lock lock) {
        this.id = id;
        this.lock = lock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public int getLockerNumber() {
        return lock.getLockerNumber();
    }

    @Override
    public String toString() {
        return "KeyCard{" + "id=" + id + ", lock=" + lock + '}';
    }
}

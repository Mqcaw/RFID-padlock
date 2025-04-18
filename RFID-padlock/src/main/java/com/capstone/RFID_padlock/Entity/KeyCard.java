package com.capstone.RFID_padlock.Entity;

import jakarta.persistence.*;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="key_cards")
public class KeyCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private List<Long> lockIDList;

    public KeyCard() {
        lockIDList = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public List<Long> getLockIDList() {
        return lockIDList;
    }

    public Long getLockId(int index) {
        return lockIDList.get(index);
    }

    public void setLockID(List<Long> lockIDList) {
        this.lockIDList = lockIDList;
    }

    public Long addLockId(Long lockID) {
        lockIDList.add(lockID);
        return lockID;
    }

    public void removeLockId(Long id) {
        lockIDList.remove(id);
    }

    public void resetList() {
        lockIDList = new ArrayList<>();
    }




    @Override
    public String toString() {
        return "KeyCard{" + "id=" + id + ", lock=" + lockIDList.toString() + '}';
    }
}

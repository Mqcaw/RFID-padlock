package com.capstone.RFID_padlock.Entity.Service;

import java.util.List;

public interface ServiceInterface<T> {

    T addEntity(T t);
    List<T> getAllEntities();
    T getEntity(Long id);
    T save(T t);
    void delete(T t);
    void delete(Long id);

}

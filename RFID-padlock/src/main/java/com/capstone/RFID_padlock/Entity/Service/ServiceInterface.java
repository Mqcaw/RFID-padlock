package com.capstone.RFID_padlock.Entity.Service;

import java.util.List;

public interface ServiceInterface<T> {

    T addEntity(T t);
    List<T> getAllEntities();

}

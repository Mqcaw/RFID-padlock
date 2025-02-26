package com.capstone.RFID_padlock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleEntityService {

    private final SimpleEntityRepository repository;

    @Autowired
    public SimpleEntityService(SimpleEntityRepository repository) {
        this.repository = repository;
    }

    public SimpleEntity addEntity(SimpleEntity entity) {
        return repository.save(entity);
    }

    public List<SimpleEntity> getAllEntities() {
        return repository.findAll();
    }
}

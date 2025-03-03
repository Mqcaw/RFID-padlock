package com.capstone.RFID_padlock.Entity.Service;

import com.capstone.RFID_padlock.Entity.Lock;
import com.capstone.RFID_padlock.Entity.Repository.LockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockService implements ServiceInterface<Lock> {

    private final LockRepository repository;

    @Autowired
    public LockService(LockRepository repository) {
        this.repository = repository;
    }

    @Override
    public Lock addEntity(Lock lock) {
        return repository.save(lock);
    }

    @Override
    public List<Lock> getAllEntities() {
        return repository.findAll();
    }
}

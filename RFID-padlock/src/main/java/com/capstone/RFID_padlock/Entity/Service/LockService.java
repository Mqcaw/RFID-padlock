package com.capstone.RFID_padlock.Entity.Service;

import com.capstone.RFID_padlock.Entity.Lock;
import com.capstone.RFID_padlock.Entity.Repository.LockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockService implements ServiceInterface<Lock> {

    private final LockRepository lockRepository;

    @Autowired
    public LockService(LockRepository repository) {
        this.lockRepository = repository;
    }

    @Override
    public Lock addEntity(Lock lock) {
        return lockRepository.save(lock);
    }

    @Override
    public List<Lock> getAllEntities() {
        return lockRepository.findAll();
    }

    @Override
    public Lock getEntity(Long id) {
        if (lockRepository.findById(id).isPresent()) {
            return lockRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public Lock save(Lock lock) {
        return lockRepository.save(lock);
    }

    @Override
    public void delete(Lock lock) {
        lockRepository.delete(lock);
    }

    @Override
    public void delete(Long id) {
        lockRepository.delete(getEntity(id));
    }
}

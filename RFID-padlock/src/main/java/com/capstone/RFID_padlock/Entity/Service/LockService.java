package com.capstone.RFID_padlock.Entity.Service;

import com.capstone.RFID_padlock.Entity.Lock;
import com.capstone.RFID_padlock.Entity.Repository.LockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockService implements ServiceInterface<Lock> {

    private final LockRepository lockRepository;
    private KeyCardService keyCardService;

    @Autowired
    public LockService(LockRepository repository, @Lazy KeyCardService keyCardService) {
        this.lockRepository = repository;
        this.keyCardService = keyCardService;
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

    public Lock synchronize(Long lockId) {
        Lock lock = getEntity(lockId);
        return synchronize(lock);
    }

    public Lock synchronize(Lock lock) {
        if (lock.getId() == null || getEntity(lock.getId()) == null) {
            return null;
        }

        if (lock.getKeyCardId() != null) {
            keyCardService.addLock(lock.getKeyCardId(), lock.getId());
        } else {
            keyCardService.getEntity(getEntity(lock.getId()).getKeyCardId()).removeLockId(lock.getId());
        }

        return save(lock);
    }

    public Lock synchronizeAdd(Lock lock) {
        //adds new lock entity to the database based on the lock param
        Lock newLock = addEntity(lock);

        //###may be able to replace with synchronize method
        //if the lock has a key card id declared it will assign the key card to the lock
        if (newLock.getKeyCardId() != null) {
            //see addLock() method for more detail
            keyCardService.addLock(newLock.getKeyCardId(), newLock.getId());
        }
        //saves
        return save(newLock);
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

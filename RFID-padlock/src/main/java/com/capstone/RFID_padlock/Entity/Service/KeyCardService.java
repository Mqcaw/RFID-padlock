package com.capstone.RFID_padlock.Entity.Service;

import com.capstone.RFID_padlock.Entity.KeyCard;
import com.capstone.RFID_padlock.Entity.Lock;
import com.capstone.RFID_padlock.Entity.Repository.KeyCardRepository;
import com.capstone.RFID_padlock.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyCardService implements ServiceInterface<KeyCard> {

    private final KeyCardRepository keyCardRepository;
    private LockService lockService;

    @Autowired
    public KeyCardService(KeyCardRepository keyCardRepository, LockService lockService) {
        this.keyCardRepository = keyCardRepository;
        this.lockService = lockService;
    }

    @Override
    public KeyCard addEntity(KeyCard keyCard) {
        return keyCardRepository.save(keyCard);
    }

    @Override
    public List<KeyCard> getAllEntities() {
        return keyCardRepository.findAll();
    }

    @Override
    public KeyCard getEntity(Long id) {
        if (id == null) {
            return null;
        }
        if (keyCardRepository.findById(id).isPresent()) {
            return keyCardRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public KeyCard save(KeyCard keyCard) {
        return keyCardRepository.save(keyCard);
    }

    @Override
    public void delete(KeyCard keyCard) {
        keyCardRepository.delete(keyCard);
    }

    @Override
    public void delete(Long id) {
        keyCardRepository.delete(getEntity(id));
    }

    public void addLock(Long keyCardId, Long lockId) {
        KeyCard keyCard = getEntity(keyCardId);
        Lock lock = lockService.getEntity(lockId);
        if (lock.getKeyCardId() != null) {
            KeyCard prevKeyCard = getEntity(lock.getKeyCardId());
            prevKeyCard.removeLockId(lock.getId());
            save(prevKeyCard);
        }

        keyCard.addLockId(lockId);
        lock.setKeyCardId(keyCardId);
        save(keyCard);
        lockService.save(lock);
    }

    public void resetList(Long id) {
        getEntity(id).resetList();
    }



}

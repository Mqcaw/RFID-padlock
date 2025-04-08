package com.capstone.RFID_padlock.Entity.Service;

import com.capstone.RFID_padlock.Entity.KeyCard;
import com.capstone.RFID_padlock.Entity.Lock;
import com.capstone.RFID_padlock.Entity.Repository.KeyCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyCardService implements ServiceInterface<KeyCard> {

    private final KeyCardRepository keyCardRepository;
    private LockService lockService;
    private StudentService studentService;

    @Autowired
    public KeyCardService(KeyCardRepository keyCardRepository, LockService lockService, @Lazy StudentService studentService) {
        this.keyCardRepository = keyCardRepository;
        this.lockService = lockService;
        this.studentService = studentService;
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
            if (getEntity(lock.getKeyCardId()) != null) {
                KeyCard prevKeyCard = getEntity(lock.getKeyCardId());
                prevKeyCard.removeLockId(lock.getId());
                save(prevKeyCard);
            }

        }

        keyCard.addLockId(lockId);
        lock.setKeyCardId(keyCardId);
        save(keyCard);
        lockService.save(lock);
    }

    public KeyCard synchronize(Long keyCardId) {
        KeyCard keyCard = getEntity(keyCardId);

        if (keyCard.getStudentId() != null) {
            studentService.assignKeyCard(keyCard.getStudentId(), keyCard.getId());
        }
        if (keyCard.getLockIDList() != null) {
            for (Long lockId : keyCard.getLockIDList()) {
                addLock(keyCard.getId(), lockId);
            }
        }
        return save(keyCard);
    }

    public KeyCard synchronize(KeyCard keyCard) {
        if (keyCard.getId() == null || getEntity(keyCard.getId()) == null) {
            return null;
        }

        if (keyCard.getStudentId() != null) {
            studentService.assignKeyCard(keyCard.getStudentId(), keyCard.getId());
        }
        if (keyCard.getLockIDList() != null) {
            for (Long lockId : keyCard.getLockIDList()) {
                addLock(keyCard.getId(), lockId);
            }
        }
        return save(keyCard);
    }

    public KeyCard synchronizeAdd(KeyCard keyCard) {
        KeyCard newKeyCard = addEntity(keyCard);

        if (newKeyCard.getStudentId() != null) {
            studentService.assignKeyCard(newKeyCard.getStudentId(), newKeyCard.getId());
        }
        if (newKeyCard.getLockIDList() != null) {
            for (Long lockId : newKeyCard.getLockIDList()) {
                addLock(newKeyCard.getId(), lockId);
            }
        }
        return save(newKeyCard);
    }

    public void resetList(Long id) {
        getEntity(id).resetList();
    }



}

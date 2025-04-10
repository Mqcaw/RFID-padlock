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

        return synchronize(keyCard);
    }

    public KeyCard synchronize(KeyCard keyCard) {
        if (keyCard.getId() == null || getEntity(keyCard.getId()) == null) {
            return null;
        }

        //checks the current instance of the key card (the new updated values), if it has a student id defined
        //this check will run even if the student value defined is the same as before the update. this doesn't matter.
        if (keyCard.getStudentId() != null) {
            //assigns/synchronizes ids across the new refrenced student, see function for more detail
            studentService.assignKeyCard(keyCard.getStudentId(), keyCard.getId());
        }

        //checks the current instance of the key card (the new updated values), if it has a lock list defined
        if (keyCard.getLockIDList() != null) {
            //loop through all locks
            for (Long lockId : keyCard.getLockIDList()) {
                //adds lock to the key card
                addLock(keyCard.getId(), lockId);
            }
        }
        return save(keyCard);
    }

    public KeyCard synchronizeAdd(KeyCard keyCard) {
        //adds new key card entity to the database based on the key card param
        KeyCard newKeyCard = addEntity(keyCard);

        return synchronize(newKeyCard);
    }

    public KeyCard resetList(Long id) {
        return getEntity(id).resetList();
    }



}

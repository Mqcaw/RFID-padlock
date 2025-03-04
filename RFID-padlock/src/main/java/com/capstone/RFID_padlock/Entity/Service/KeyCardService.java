package com.capstone.RFID_padlock.Entity.Service;

import com.capstone.RFID_padlock.Entity.KeyCard;
import com.capstone.RFID_padlock.Entity.Repository.KeyCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyCardService implements ServiceInterface<KeyCard> {

    private final KeyCardRepository keyCardRepository;

    @Autowired
    public KeyCardService(KeyCardRepository keyCardRepository) {
        this.keyCardRepository = keyCardRepository;
    }

    @Override
    public KeyCard addEntity(KeyCard keyCard) {
        return keyCardRepository.save(keyCard);
    }

    @Override
    public List<KeyCard> getAllEntities() {
        return keyCardRepository.findAll();
    }
}

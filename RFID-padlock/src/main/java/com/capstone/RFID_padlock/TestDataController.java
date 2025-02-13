package com.capstone.RFID_padlock;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor")
public class TestDataController {

    @Autowired
    private TestDataRepository repository;

    @PostMapping("/send")
    public TestData receiveData(@RequestBody TestData data) {
        return repository.save(data);
    }

    @GetMapping("/all")
    public List<TestData> getAllData() {
        return repository.findAll();
    }
}

package com.capstone.RFID_padlock;

import com.capstone.RFID_padlock.Entity.KeyCard;
import com.capstone.RFID_padlock.Entity.Lock;
import com.capstone.RFID_padlock.Entity.Service.KeyCardService;
import com.capstone.RFID_padlock.Entity.Service.LockService;
import com.capstone.RFID_padlock.Entity.Service.StudentService;
import com.capstone.RFID_padlock.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.List;

//curl http://localhost:8081/actuator/health
//Invoke-WebRequest -Uri "http://localhost:8081/api/locks" -Method Get
//Invoke-WebRequest -Uri "http://localhost:8081/api/key_cards" -Method Get
//Invoke-WebRequest -Uri "http://localhost:8081/api/students" -Method Get

// mysqlsh
// \sql
// \connect user:pass@host:port


@Controller
@RequestMapping("/api")
public class ApiController {

    private LockService lockService;
    private KeyCardService keyCardService;
    private StudentService studentService;


    @Autowired
    public void ApiController(LockService lockService, KeyCardService keyCardService, StudentService studentService) {
        this.lockService = lockService;
        this.keyCardService = keyCardService;
        this.studentService = studentService;
    }


    //#################LOCK API###################
    @GetMapping("/locks")
    @ResponseBody
    public List<Lock> getAllLocks() {
        return lockService.getAllEntities();
    }

    @PostMapping("/locks")
    @ResponseBody
    public Lock createNewLock(@RequestBody Lock lock) {
        return lockService.addEntity(lock);
    }

    @GetMapping("/locks/{id}")
    @ResponseBody
    public Lock getLock(@PathVariable("id") Long id) {
        Lock lock = lockService.getEntity(id);

        if (lock == null) {
            return null;
        }
        return lock;
    }

    @PutMapping("/locks/{id}")
    @ResponseBody
    public Lock updateLock(@PathVariable("id") Long id, @RequestBody Lock lock) {
        Lock existingLock = lockService.getEntity(id);
        if (existingLock == null) {
            return null;
        }

        existingLock = lockService.getEntity(id).updateData(lock);
        return lockService.save(existingLock);
    }

    @DeleteMapping("/locks/{id}")
    public void deleteLock(@PathVariable("id") Long id) {
        lockService.delete(id);
    }

    //#################KEYCARD API###################

    @GetMapping("/key_cards")
    @ResponseBody
    public List<KeyCard> getAllKeyCards() {
        return keyCardService.getAllEntities();
    }

    @PostMapping("/key_cards")
    @ResponseBody
    public KeyCard createNewKeyCard(@RequestBody KeyCard keyCard) {
        return keyCardService.addEntity(keyCard);
    }

    @GetMapping("/key_cards/{id}")
    @ResponseBody
    public KeyCard getKeyCard(@PathVariable("id") Long id) {
        KeyCard keyCard = keyCardService.getEntity(id);

        if (keyCard == null) {
            return null;
        }
        return keyCard;
    }

    @PutMapping("/key_cards/{id}")
    @ResponseBody
    public KeyCard updateKeyCard(@PathVariable("id") Long id, @RequestBody KeyCard keyCard) {
        KeyCard existingKeyCard = keyCardService.getEntity(id);
        if (existingKeyCard == null) {
            return null;
        }

        existingKeyCard = keyCardService.getEntity(id).updateData(keyCard);
        return keyCardService.save(existingKeyCard);
    }

    @DeleteMapping("/key_cards/{id}")
    public void deleteKeyCard(@PathVariable("id") Long id) {
        keyCardService.delete(id);
    }


    //#################STUDENT API###################

    @GetMapping("/students")
    @ResponseBody
    public List<Student> getAllStudents() {
        return studentService.getAllEntities();
    }

    @PostMapping("/students")
    @ResponseBody
    public Student createNewStudent(@RequestBody Student student) {
        return studentService.addEntity(student);
    }

    @GetMapping("/students/{id}")
    @ResponseBody
    public Student getStudent(@PathVariable("id") Long id) {
        Student student = studentService.getEntity(id);

        if (student == null) {
            return null;
        }
        return student;
    }

    @PutMapping("/students/{id}")
    @ResponseBody
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        Student existingStudent = studentService.getEntity(id);
        if (existingStudent == null) {
            return null;
        }

        existingStudent = studentService.getEntity(id).updateData(student);
        return studentService.save(existingStudent);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentService.delete(id);
    }



    //#################EXTRA API###################


    @PostMapping("/locks/{id}/access-check")
    @ResponseBody
    public boolean accessGranted(@PathVariable("id") Long id) {
        if (lockService.getEntity(id) == null) return false;
        return true;
    }



}

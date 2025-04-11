package com.capstone.RFID_padlock;

import com.capstone.RFID_padlock.Entity.KeyCard;
import com.capstone.RFID_padlock.Entity.Lock;
import com.capstone.RFID_padlock.Entity.Service.KeyCardService;
import com.capstone.RFID_padlock.Entity.Service.LockService;
import com.capstone.RFID_padlock.Entity.Service.StudentService;
import com.capstone.RFID_padlock.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

//Health
//curl http://localhost:8081/actuator/health

//CRUD commands for reference, Windows PowerShell

//Create
//Invoke-RestMethod -Uri "http://localhost:8081/api/locks" -Method Post -Headers @{"Content-Type"="application/json"} -Body ('{"lockerNumber":234}')
//Invoke-RestMethod -Uri "http://localhost:8081/api/students" -Method Post -Headers @{"Content-Type"="application/json"} -Body ('{"id":6700, "name":"Jackson Funk", "grade":12}')
//Invoke-RestMethod -Uri "http://localhost:8081/api/key_cards" -Method Post -Headers @{"Content-Type"="application/json"} -Body ('{}' | Out-String)

//Read
//Invoke-RestMethod -Uri "http://localhost:8081/api/locks" -Method Get
//Invoke-RestMethod -Uri "http://localhost:8081/api/locks/1" -Method Get

//Update

//Invoke-RestMethod -Uri "http://localhost:8081/api/locks/1" -Method Put -Headers @{"Content-Type"="application/json"} -Body ('{"keyCardId":1}' | Out-String)
//this will null or 0 all other entries other than keyCardId and id

//Delete
//TODO: fix delete - see key card implementation for example on how to fix
//TODO: make deleting an entity unlink from assigned entities
//TODO: make reset list synchronous

//Extra Delete
//Invoke-RestMethod -Uri "http://localhost:8081/api/key_cards/8/reset_list" -Method Post

//Assign
//Invoke-RestMethod -Uri "http://localhost:8081/api/assign/lock?lockId=2&keyCardId=1" -Method Post
//Invoke-RestMethod -Uri "http://localhost:8081/api/assign/key-card?keyCardId=1&studentId=6700" -Method Post



//TODO: add comments
//TODO: more extensive testing, mostly when creating/updating

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
        return lockService.synchronizeAdd(lock);
    }

    @GetMapping("/locks/{id}")
    @ResponseBody
    public Lock getLock(@PathVariable("id") Long id) {
        return lockService.getEntity(id);
    }

    @PutMapping("/locks/{id}")
    @ResponseBody
    public Lock updateLock(@PathVariable("id") Long id, @RequestBody Lock lock) {
        lock.setId(id);

        return lockService.synchronize(lock);
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
        //if a reference id gets defined in the new student, it will synchronize
        //see the synchronizeAdd method for more detail
        return keyCardService.synchronizeAdd(keyCard);

    }

    @GetMapping("/key_cards/{id}")
    @ResponseBody
    public KeyCard getKeyCard(@PathVariable("id") Long id) {
        return keyCardService.getEntity(id);
    }

    @PutMapping("/key_cards/{id}")
    @ResponseBody
    public KeyCard updateKeyCard(@PathVariable("id") Long id, @RequestBody KeyCard keyCard) {
        //fail-safe to make sure the key card entity defined has the correct id.
        //?###might be removable
        keyCard.setId(id);
        System.out.println("########################" + keyCard.getLockIDList());

        return keyCardService.synchronize(keyCard);
    }

    @DeleteMapping("/key_cards/{id}")
    @ResponseBody
    public List<KeyCard> deleteKeyCard(@PathVariable("id") Long id) {
        keyCardService.delete(id);
        return keyCardService.getAllEntities();
    }

    @PostMapping("/key_cards/{id}/reset_list")
    @ResponseBody
    public void resetKeyCardList(@PathVariable("id") Long id) {
        keyCardService.save(keyCardService.resetList(id));
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
        //if a reference id gets defined in the new student, it will synchronize
        //see the synchronizeAdd method for more detail
        return studentService.synchronizeAdd(student);
    }

    @GetMapping("/students/{id}")
    @ResponseBody
    public Student getStudent(@PathVariable("id") Long id) {
        return studentService.getEntity(id);
    }

    @PutMapping("/students/{id}")
    @ResponseBody
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        //fail-safe to make sure the student entity defined has the correct id.
        //?###may be removable
        student.setId(id);

        return studentService.synchronize(student);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentService.delete(id);
    }



    //#################EXTRA API###################

    //### expand access check to have the lock sync its info (maybe?), note: idk where I was going with this
    @PostMapping("/locks/{id}/access-check")
    @ResponseBody
    public boolean accessGranted(@PathVariable("id") Long id) {
        Lock lock = lockService.getEntity(id);
        if (lock.getKeyCardId() == null) return false;
        return keyCardService.getEntity(lock.getKeyCardId()).getStudentId() != null;
    }

    @PostMapping("/assign/lock")
    @ResponseBody
    public void assignLock(@RequestParam(name="lockId") Long lockId, @RequestParam(name="keyCardId") Long keyCardId) {
        keyCardService.addLock(keyCardId, lockId);
    }
    @PostMapping("/assign/key-card")
    @ResponseBody
    public void assignKeyCard(@RequestParam(name="keyCardId") Long keyCardId, @RequestParam(name="studentId") Long studentId, Model model) {
        studentService.assignKeyCard(studentId, keyCardId);
    }


}

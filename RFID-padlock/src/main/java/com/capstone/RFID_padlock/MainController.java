package com.capstone.RFID_padlock;

import com.capstone.RFID_padlock.Entity.KeyCard;
import com.capstone.RFID_padlock.Entity.Lock;
import com.capstone.RFID_padlock.Entity.Service.KeyCardService;
import com.capstone.RFID_padlock.Entity.Service.LockService;
import com.capstone.RFID_padlock.Entity.Service.StudentService;
import com.capstone.RFID_padlock.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class MainController {

    private LockService lockService;
    private KeyCardService keyCardService;
    private StudentService studentService;


    @Autowired
    public void MainController(LockService lockService, KeyCardService keyCardService, StudentService studentService) {
        this.lockService = lockService;
        this.keyCardService = keyCardService;
        this.studentService = studentService;
    }



    @GetMapping("/")
    public String greeting(@RequestParam(name="test", required=false, defaultValue="World") String var, Model model) {
        model.addAttribute("name", var);
        List<Student> students = studentService.getAllEntities();
        model.addAttribute("students", students);
        return "index";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/registry")
    public String registry(Model model) {
        List<Student> students = studentService.getAllEntities();
        List<KeyCard> keyCards = keyCardService.getAllEntities();
        List<Lock> locks = lockService.getAllEntities();
        Map<Long, String> studentLocksMap = new HashMap<>();
        Map<Long, String> studentLockerNumberMap = new HashMap<>();

        for (Student student : students) {
            KeyCard keyCard = keyCardService.getEntity(student.getKeyCardId());
            if (keyCard == null) {
                continue;
            }
            studentLocksMap.put(student.getId(), keyCard.getLockIDList().toString());
        }

        for (Student student : students) {
            KeyCard keyCard = keyCardService.getEntity(student.getKeyCardId());
            String string = "[";
            if (keyCard == null) {
                continue;
            }
            List<Integer> numberList = new ArrayList<>();
            for (Long id : keyCard.getLockIDList()) {
                if (id == null) {
                    continue;
                }
                numberList.add(lockService.getEntity(id).getLockerNumber());
            }
            studentLockerNumberMap.put(student.getId(), numberList.toString());
        }



        model.addAttribute("students", students);
        model.addAttribute("keycards", keyCards);
        model.addAttribute("locks", locks);
        model.addAttribute("studentLocksMap", studentLocksMap);
        model.addAttribute("studentLockerNumberMap", studentLockerNumberMap);

        return "registry";
    }

    @GetMapping("/locks")
    public String locks(Model model) {
        model.addAttribute("locks", lockService.getAllEntities());
        return "locks";
    }

    @GetMapping("/key-cards")
    public String keyCards(Model model) {
        model.addAttribute("keyCards", keyCardService.getAllEntities());
        return "key-cards";
    }

    @GetMapping("/student/{id}")
    public String student(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", studentService.getEntity(id));
        return "student";
    }

    @GetMapping("/lock/{id}")
    public String lock(@PathVariable("id") Long id, Model model) {
        model.addAttribute("lock", lockService.getEntity(id));
        return "lock";
    }






}
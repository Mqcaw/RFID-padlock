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

import java.util.List;


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

    @GetMapping("/students")
    public String students(Model model) {
        List<Student> students = studentService.getAllEntities();
        model.addAttribute("students", students);
        return "students";
    }




}
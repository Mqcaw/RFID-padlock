package com.capstone.RFID_padlock;

import com.capstone.RFID_padlock.Entity.KeyCard;
import com.capstone.RFID_padlock.Entity.Lock;
import com.capstone.RFID_padlock.Entity.Service.KeyCardService;
import com.capstone.RFID_padlock.Entity.Service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class MainController {

    private LockService lockService;
    private KeyCardService keyCardService;


    @Autowired
    public void MainController(LockService lockService, KeyCardService keyCardService) {
        this.lockService = lockService;
        this.keyCardService = keyCardService;
    }



    @GetMapping("/")
    public String greeting(@RequestParam(name="test", required=false, defaultValue="World") String var, Model model) {
        model.addAttribute("name", var);
        return "index";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }




}
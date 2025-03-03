package com.capstone.RFID_padlock;

import com.capstone.RFID_padlock.Entity.Lock;
import com.capstone.RFID_padlock.Entity.Service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//curl http://localhost:8081/actuator/health
//Invoke-WebRequest -Uri "http://localhost:8081/all" -Method Get

// mysqlsh
// \sql
// \connect user:pass@host:port


@Controller
public class MainController {

    private LockService lockService;


    @Autowired
    public void MainController(LockService lockService) {
        this.lockService = lockService;
    }





    @GetMapping("/send/lock")
    public String send() {
        return "send_lock";
    }

    @PostMapping("/send/lock")
    public String addEntity(@ModelAttribute Lock lock, Model model) {
        lockService.addEntity(lock);
        return "redirect:/success";
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

    @GetMapping("/all/lock")
    public String getAllData(Model model) {
        List<Lock> allLocks = lockService.getAllEntities();

        model.addAttribute("locks", allLocks);

        return "view_locks";
    }


}

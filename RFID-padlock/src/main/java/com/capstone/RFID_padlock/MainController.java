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

//curl http://localhost:8081/actuator/health
//Invoke-WebRequest -Uri "http://localhost:8081/all" -Method Get

// mysqlsh
// \sql
// \connect user:pass@host:port


@Controller
public class MainController {

    private LockService lockService;
    private KeyCardService keyCardService;


    @Autowired
    public void MainController(LockService lockService, KeyCardService keyCardService) {
        this.lockService = lockService;
        this.keyCardService = keyCardService;
    }





    @GetMapping("/send/lock")
    public String sendLock() {
        return "send_lock";
    }

    @GetMapping("/send/key_card")
    public String sendKeyCard() {
        return "send_key_card";
    }


    @PostMapping("/send/lock")
    public String addEntity(@ModelAttribute Lock lock, Model model) {
        lockService.addEntity(lock);
        return "redirect:/success";
    }

    @PostMapping("/send/key_card")
    public String addEntity(@ModelAttribute KeyCard keyCard, Model model) {
        keyCardService.addEntity(keyCard);
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
    public String getAllLocks(Model model) {
        List<Lock> allLocks = lockService.getAllEntities();

        model.addAttribute("locks", allLocks);

        return "view_locks";
    }

        @GetMapping("/all/lock/{id}")
        public String showLockDetails(@PathVariable("id") Long id, Model model) {
            Lock lock = lockService.getEntity(id);

            if (lock != null) {
                model.addAttribute("lock", lock);
                return "lock_details";
            } else {
                return "redirect:/all/lock";
            }
        }



    @GetMapping("/all/key_card")
    public String getAllKeyCards(Model model) {
        List<KeyCard> allKeyCards = keyCardService.getAllEntities();
        System.out.println(allKeyCards.toString());
        model.addAttribute("key_cards", allKeyCards);

        return "view_key_cards";
    }


}

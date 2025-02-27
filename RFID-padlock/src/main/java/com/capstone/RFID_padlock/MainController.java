package com.capstone.RFID_padlock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//curl http://localhost:8081/actuator/health
//Invoke-WebRequest -Uri "http://localhost:8081/all" -Method Get

// mysqlsh
// \sql
// \connect user:pass@host:port


@Controller
public class MainController {

    private SimpleEntityService service;


    @GetMapping("/")
    public String greeting(@RequestParam(name="test", required=false, defaultValue="World") String var, Model model) {
        model.addAttribute("name", var);
        return "index";
    }


    @Autowired
    public void SimpleEntityController(SimpleEntityService service) {
        this.service = service;
    }

    @GetMapping("/send")
    public String send() {
        return "send";
    }

    @PostMapping("/send")
    public SimpleEntity addEntity(@RequestBody SimpleEntity entity) {
        return service.addEntity(entity);
    }

    @ResponseBody
    @GetMapping("/all")
    public String getAllData() {
        StringBuilder result = new StringBuilder();
        for (SimpleEntity item : service.getAllEntities()) {
            result.append(item.toString()).append("\n");
        }
        return result.toString();
    }


}

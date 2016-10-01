package com.anakiou.web;

import com.anakiou.domain.Input;
import com.anakiou.service.PifaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/input")
public class InputController {

    private final PifaceService pifaceService;

    @Autowired
    public InputController(PifaceService pifaceService) {
        this.pifaceService = pifaceService;
    }

    @GetMapping
    public List<Input> get(){
        return pifaceService.getInputs();
    }

    @GetMapping("/names")
    public List<String> getName(){
        return pifaceService.getInputNames();
    }

    @GetMapping("/{no}")
    public int status(@PathVariable("no")int no){
        return pifaceService.getInputStatus(no);
    }


    @PostMapping("/{no}")
    public String setName(@PathVariable("no")int no, @RequestParam("name") String name){
        return pifaceService.setInputName(no, name);
    }


}

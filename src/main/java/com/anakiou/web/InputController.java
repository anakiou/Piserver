package com.anakiou.web;

import com.anakiou.domain.Input;
import com.anakiou.service.PifaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/input")
public class InputController {

    private final PifaceService pifaceService;

    @Autowired
    public InputController(PifaceService pifaceService) {
        this.pifaceService = pifaceService;
    }

    @GetMapping
    public List<Input> get() {
        return pifaceService.getInputs();
    }

    @GetMapping("/names")
    public List<String> getNames() {
        return pifaceService.getInputNames();
    }

    @GetMapping("/{no}")
    public int getSingleStatus(@PathVariable("no") int no) {
        return pifaceService.getInputStatus(no);
    }

    @GetMapping("/status")
    public int[] getAllStatus() {
        return pifaceService.getInputStatus();
    }

    @PostMapping("/{no}")
    public String setName(@PathVariable("no") int no, @RequestParam("name") String name) {
        return pifaceService.setInputName(no, name);
    }


}

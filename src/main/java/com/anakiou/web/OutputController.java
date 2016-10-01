package com.anakiou.web;

import com.anakiou.domain.Output;
import com.anakiou.service.PifaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/output")
public class OutputController {

    private final PifaceService pifaceService;

    @Autowired
    public OutputController(PifaceService pifaceService) {
        this.pifaceService = pifaceService;
    }

    @GetMapping("/{no}")
    public int status(@PathVariable("no") int no){
        return pifaceService.getOutputStatus(no);
    }

    @GetMapping
    public List<Output> get(){
        return pifaceService.getOutputs();
    }

    @GetMapping("/names")
    public List<String> getName(){
        return pifaceService.getOutputNames();
    }

    @PostMapping("/{no}/{value}")
    public int control(@PathVariable("no") int no, @PathVariable("value")boolean value){
        return pifaceService.setOutput(no, value);
    }

    @PostMapping("/{no}")
    public String setName(@PathVariable("no")int no, @RequestParam("name") String name){
        return pifaceService.setOutputName(no, name);
    }
}

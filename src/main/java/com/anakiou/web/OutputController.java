package com.anakiou.web;

import com.anakiou.domain.Output;
import com.anakiou.service.PifaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/output")
public class OutputController {

    private final PifaceService pifaceService;

    @Autowired
    public OutputController(PifaceService pifaceService) {
        this.pifaceService = pifaceService;
    }

    @GetMapping
    public List<Output> get() {
        return pifaceService.getOutputs();
    }

    @GetMapping("/names")
    public List<String> getNames() {
        return pifaceService.getOutputNames();
    }

    @GetMapping("/{no}")
    public int getSingleStatus(@PathVariable("no") int no) {
        return pifaceService.getOutputStatus(no);
    }

    @GetMapping("/status")
    public int[] getAllStatus() {
        return pifaceService.getOutputStatus();
    }

    @PostMapping("/{no}")
    public String setName(@PathVariable("no") int no, @RequestParam("name") String name) {
        return pifaceService.setOutputName(no, name);
    }

    @PostMapping("/{no}/{value}")
    public int setControl(@PathVariable("no") int no, @PathVariable("value") boolean value) {
        return pifaceService.setOutput(no, value);
    }

    @PostMapping("/all")
    public int[] setAllControls(@RequestParam("values") boolean[] values) {
        return pifaceService.setOutput(values);
    }


}

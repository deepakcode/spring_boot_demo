package com.example.demo.netwok_calls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    ParallelCallsService parallelCallService;

    @Autowired
    NetworkCallService networkCallService;

    @GetMapping("/pcalls")
    public String test() throws InterruptedException {
        return parallelCallService.parallel_calls().join();
    }

    @GetMapping("/ncalls")
    public String testt() throws InterruptedException {
        return networkCallService.getData().getBody();
    }
}

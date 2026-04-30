package cat.itacademy.s04.t01.userapi.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {
    @GetMapping("/health")
    public Status getOk() {
        return new Status("OK");
    }

}

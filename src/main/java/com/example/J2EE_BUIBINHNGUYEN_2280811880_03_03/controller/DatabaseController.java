package com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.controller;

import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.service.DatabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/db")
public class DatabaseController {
    private final DatabaseService databaseService;

    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return databaseService.ping()
                ? ResponseEntity.ok("OK")
                : ResponseEntity.status(500).body("FAIL");
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        return ResponseEntity.ok(databaseService.info());
    }
}

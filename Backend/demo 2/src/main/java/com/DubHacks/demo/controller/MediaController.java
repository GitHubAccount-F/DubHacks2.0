package com.DubHacks.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class MediaController {

    @GetMapping("/") // update string to an object
    public ResponseEntity<String> home() {
        return null;
    }

    @GetMapping("/media/random")
    public ResponseEntity<String> random() {
        return null;
    }

    @GetMapping("/media/{id}")
    public ResponseEntity<String> media(@PathVariable("id") String id) {
        return null;
    }

    @GetMapping("/media/search/{keywords}")
    public ResponseEntity<String> search(@PathVariable List<String> keywords) {
        return ResponseEntity.ok("Searching for: " + keywords);
    }

}

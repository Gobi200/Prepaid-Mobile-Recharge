package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.OttPlatform;
import com.example.demo.service.OttPlatformService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/Ott-Platform")
public class OttPlatformController {
	
	@Autowired
	OttPlatformService ottPlatformService;
	
	 // Get all OTT platforms
    @GetMapping
    public List<OttPlatform> getAllOttPlatforms() {
        return ottPlatformService.getAllOttPlatforms();
    }

    // Get OTT platform by ID
    @GetMapping("/{id}")
    public Optional<OttPlatform> getOttPlatformById(@PathVariable String id) {
        return ottPlatformService.getOttPlatformById(id);
    }

    @PostMapping
    public ResponseEntity<String> addOttPlatform(@RequestBody OttPlatform ottPlatform) {
        String result = ottPlatformService.addOttPlatform(ottPlatform);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

	 // Update an OTT platform
	    @PutMapping("/{id}")
	    public ResponseEntity<String> updateOttPlatform(@PathVariable String id, @RequestBody OttPlatform ottPlatform) {
	        try {
	            String result = ottPlatformService.updateOttPlatform(id, ottPlatform);
	            return ResponseEntity.ok(result); // 200 OK
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTT Platform not found"); // 404 Not Found
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data"); // 400 Bad Request
	        }
	    }

    // Delete an OTT platform
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOttPlatform(@PathVariable String id) {
        try {
            String result = ottPlatformService.deleteOttPlatform(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTT Platform not found"); // 404 Not Found
        }
    }
}

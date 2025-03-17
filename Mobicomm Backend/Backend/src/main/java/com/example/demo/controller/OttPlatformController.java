package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    // Add a new OTT platform
    @PostMapping
    public String addOttPlatform(@RequestBody OttPlatform ottPlatform) {
        return ottPlatformService.addOttPlatform(ottPlatform);
    }

    // Update an OTT platform
    @PutMapping("/{id}")
    public String updateOttPlatform(@PathVariable String id, @RequestBody OttPlatform ottPlatform) {
        return ottPlatformService.updateOttPlatform(id, ottPlatform);
    }

    // Delete an OTT platform
    @DeleteMapping("/{id}")
    public String deleteOttPlatform(@PathVariable String id) {
        return ottPlatformService.deleteOttPlatform(id);
    }
}

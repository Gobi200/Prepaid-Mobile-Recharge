package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OttPlatform;
import com.example.demo.repository.OttPlatformRepository;

@Service
public class OttPlatformService {
	
	@Autowired
	OttPlatformRepository ottPlatformRepo;
	
	private String generateOttId() {
        OttPlatform lastCategory = ottPlatformRepo.findTopByOrderByOttIdDesc();
        
        if (lastCategory != null) {
            String lastId = lastCategory.getOttId(); // e.g., "MBC_10"
            int lastNumber = Integer.parseInt(lastId.replace("MB_OTT_", "")); // Extract numeric part
            return "MB_OTT_" + (lastNumber + 1); // Generate new ID
        } else {
            return "MB_OTT_1"; // First record
        }
    }
	
	// Get all OTT platforms
    public List<OttPlatform> getAllOttPlatforms() {
        return ottPlatformRepo.findAll();
    }

    // Get OTT platform by ID
    public Optional<OttPlatform> getOttPlatformById(String id) {
        return ottPlatformRepo.findById(id);
    }

    // Add a new OTT platform
    public String addOttPlatform(OttPlatform ottPlatform) {
    	ottPlatform.setOttId(generateOttId());
    	ottPlatformRepo.save(ottPlatform);
        return "OTT Platform Added Successfully!";
    }

    // Update an existing OTT platform
    public String updateOttPlatform(String id, OttPlatform updatedOtt) {
        if (!ottPlatformRepo.existsById(id)) {
            throw new RuntimeException("OTT Platform ID Doesn't Exist!");
        }

        OttPlatform existingOtt = ottPlatformRepo.findById(id).get();
        existingOtt.setPlatformName(updatedOtt.getPlatformName());
        existingOtt.setIcons(updatedOtt.getIcons());
        ottPlatformRepo.save(existingOtt);
        
        return "OTT Platform Updated Successfully!";
    }

    // Delete an OTT platform by ID
    public String deleteOttPlatform(String id) {
        if (!ottPlatformRepo.existsById(id)) {
            throw new RuntimeException("OTT Platform ID Doesn't Exist!");
        }
        ottPlatformRepo.deleteById(id);
        return "OTT Platform Deleted Successfully!";
    }
}

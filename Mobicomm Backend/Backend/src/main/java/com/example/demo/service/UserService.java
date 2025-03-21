package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    private String generateUserId() {
        User lastUser = userRepository.findTopByOrderByUserIdDesc();
        
        if (lastUser != null && lastUser.getUserId() != null) {
            String lastId = lastUser.getUserId();
            int lastNumber = Integer.parseInt(lastId.substring(8)); // Extract numeric part after "MB_USER_"
            int newNumber = lastNumber + 1;
            
            return String.format("MB_USER_%04d", newNumber); // Pad with leading zeros to 4 digits
        } 
        else {
            return "MB_USER_0001"; // Initial ID with 4-digit padding
        }
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }
    
    // Method to fetch user_id by mobile number
    public String getUserIdByMobile(String mobile) {
        Optional<User> userOptional = userRepository.findByMobile(mobile);
        if (userOptional.isPresent()) {
            return userOptional.get().getUserId(); // Return the user_id
        } else {
            throw new RuntimeException("User not found with mobile number: " + mobile);
        }
    }
    
    public User createUser(User user) {
        user.setUserId(generateUserId());
        return userRepository.save(user);
    }

    public User updateUser(String id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setMobile(userDetails.getMobile());
            user.setAadharNo(userDetails.getAadharNo());
            user.setDob(userDetails.getDob());
            user.setAddress(userDetails.getAddress());
            user.setCity(userDetails.getCity());
            user.setPincode(userDetails.getPincode());
            user.setState(userDetails.getState());
            user.setCountry(userDetails.getCountry());
            return userRepository.save(user);
        }).orElse(null);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public boolean isMobileRegistered(String mobile) {
        return userRepository.existsByMobile(mobile);
    }

	public Optional<User> findByMobile(String mobile) {
		return userRepository.findByMobile(mobile);
	}
}
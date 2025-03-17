package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Category;
import com.example.demo.model.Status;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepo;
	
	private String generateCategoryId() {
        Category lastCategory = categoryRepo.findTopByOrderByCategoryIdDesc();
        
        if (lastCategory != null) {
            String lastId = lastCategory.getCategoryId(); // e.g., "MBC_10"
            int lastNumber = Integer.parseInt(lastId.replace("MBC_", "")); // Extract numeric part
            return "MBC_" + (lastNumber + 1); // Generate new ID
        } else {
            return "MBC_1"; // First record
        }
    }
	
	public List<Category> showCategory() {
		return categoryRepo.findAll();
	}

	public Category addCategory(Category category) {
		// Set status to ACTIVE if not explicitly set
        if (category.getStatus() == null) {
        	category.setStatus(Status.ACTIVE);
        }
		category.setCategoryId(generateCategoryId());
		categoryRepo.save(category);
		return category;
	}

	public void deleteCategory(String id) {
		categoryRepo.deleteById(id);		
	}

	public Category updateCategory(String id, Category category) {
		if (!categoryRepo.existsById(id)) {
			throw new RuntimeException("Category Id Doesn't Exist!");
	    }
		Category existingCategory = categoryRepo.findById(id).get();
		existingCategory.setCategoryName(category.getCategoryName());
		categoryRepo.save(existingCategory);
		return existingCategory;
	}

	public void activateCategory(String catId) {
        Optional<Category> optionalCategory = categoryRepo.findById(catId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setStatus(Status.ACTIVE);
            // Activate all associated plans
            category.getPlans().forEach(plan -> plan.setStatus(Status.ACTIVE));
            categoryRepo.save(category);
        } else {
            throw new RuntimeException("Plan not found with ID: " + catId);
        }
    }
    
    public void deactivateCategory(String catid) {
        Optional<Category> optionalCategory = categoryRepo.findById(catid);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setStatus(Status.INACTIVE);
            // Deactivate all associated plans
            category.getPlans().forEach(plan -> plan.setStatus(Status.INACTIVE));
            categoryRepo.save(category);
        } else {
            throw new RuntimeException("Plan not found with ID: " + catid);
        }
    }

	public Category getCategoryById(String id) {
		return categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
	}

}

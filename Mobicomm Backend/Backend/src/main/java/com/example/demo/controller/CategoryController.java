package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> showCategory() {
        List<Category> categories = categoryService.showCategory();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String id){
    	return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    
    @PostMapping
    public  ResponseEntity<Category> addCategory(@RequestBody Category category) {
    	Category savedCategory = categoryService.addCategory(category);
    	 return ResponseEntity.ok(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    @PutMapping("/{id}/active")
    public ResponseEntity<String> activateCategory(@PathVariable String id) {
        categoryService.activateCategory(id);
        return ResponseEntity.ok("Category " + id + " Activated Successfully");
    }

    @PutMapping("/{id}/inactive")
    public ResponseEntity<String> deactivateCategory(@PathVariable String id) {
        categoryService.deactivateCategory(id);
        return ResponseEntity.ok("Category " + id + " Deactivated Successfully");
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category Deleted Successfully");
    }
}

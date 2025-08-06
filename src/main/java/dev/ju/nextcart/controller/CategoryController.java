package dev.ju.nextcart.controller;

import dev.ju.nextcart.model.Category;
import dev.ju.nextcart.response.ApiResponse;
import dev.ju.nextcart.service.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    public static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    public static final HttpStatus CONFLICT = HttpStatus.CONFLICT;
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse("Categories found!", categories));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category request) {
        Category category = categoryService.addCategory(request);
        return ResponseEntity.ok(new ApiResponse("Added category successfully!", category));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category request, @PathVariable Long categoryId) {
        Category category = categoryService.updateCategory(request, categoryId);
        return ResponseEntity.ok(new ApiResponse("Updated category successfully!", category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("Category found!", category));
    }

    @GetMapping("by-name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(new ApiResponse("Category found!", category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteByCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully!!", null));
    }
}

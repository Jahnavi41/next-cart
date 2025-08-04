package dev.ju.nextcart.service.category;

import dev.ju.nextcart.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long categoryId);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long categoryId);
    void deleteCategoryById(Long categoryId);
}

package dev.ju.nextcart.service.category;

import dev.ju.nextcart.exceptions.BadRequestException;
import dev.ju.nextcart.model.Category;
import dev.ju.nextcart.repository.CategoryRepository;

import java.util.List;

public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new BadRequestException("Category with ID: "+categoryId+" does not exist!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new BadRequestException("Category with name: "+name+" does not exist!"));
    }

    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }

    @Override
    public Category addCategory(Category category) {
        return null;
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        return null;
    }

    @Override
    public void deleteCategoryById(Long categoryId) {

    }
}

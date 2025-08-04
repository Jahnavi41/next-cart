package dev.ju.nextcart.service.category;

import dev.ju.nextcart.exceptions.BadRequestException;
import dev.ju.nextcart.model.Category;
import dev.ju.nextcart.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

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
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        if(categoryRepository.existsByName(category.getName())) {
            throw new BadRequestException("Category already exists!");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                }).orElseThrow(() -> new BadRequestException("Category not found!"));
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        categoryRepository.findById(categoryId)
                .ifPresentOrElse(categoryRepository :: delete, () -> {throw new BadRequestException("Category with ID: "+categoryId+" does not exist!");});
    }
}

package dev.ju.nextcart.service.product;

import dev.ju.nextcart.exceptions.BadRequestException;
import dev.ju.nextcart.model.Category;
import dev.ju.nextcart.model.Product;
import dev.ju.nextcart.repository.CategoryRepository;
import dev.ju.nextcart.repository.ProductRepository;
import dev.ju.nextcart.request.AddProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product addProduct(AddProductRequest product) {
        Category category = categoryRepository.findByName(product.getCategory())
                .orElseGet(() ->
                {
                    Category category1 = new Category();
                    category1.setName(product.getCategory().getName());
                    return categoryRepository.save(category1);
                });

        product.setCategory(category);
        return productRepository.save(createProduct(product));
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new BadRequestException("Product with ID: "+id+" does not exist!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository :: delete,
                        () -> {throw new BadRequestException("Product with ID: "+id+" does not exist!");});
    }

    @Override
    public void updateProduct(Product product, Long productId) {

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    private Product createProduct(AddProductRequest request) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                request.getCategory()
        );
    }
}

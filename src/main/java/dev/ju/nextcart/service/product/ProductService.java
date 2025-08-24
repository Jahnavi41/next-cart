package dev.ju.nextcart.service.product;

import dev.ju.nextcart.exceptions.BadRequestException;
import dev.ju.nextcart.model.Category;
import dev.ju.nextcart.model.Product;
import dev.ju.nextcart.repository.CategoryRepository;
import dev.ju.nextcart.repository.ProductRepository;
import dev.ju.nextcart.request.AddProductRequest;
import dev.ju.nextcart.request.UpdateProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
        log.info("entered service");
        Category category = categoryRepository.findByName(product.getCategory())
                .orElseGet(() ->
                {
                    log.info("creating new category");
                    Category category1 = new Category();
                    category1.setName(product.getCategory());
                    return categoryRepository.save(category1);
                });
        log.info("saving it!");
        return productRepository.save(createProduct(product, category));
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
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow( () -> new BadRequestException("Product with ID: "+productId+" does not exist!"));
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

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());

        Category category = categoryRepository.findByName(request.getCategory().getName()).orElseThrow( () -> new BadRequestException("Product could not be updated as given category: "+request.getCategory().getName()+" does not exist!"));
        existingProduct.setCategory(category);
        return existingProduct;
    }
}

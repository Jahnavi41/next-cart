package dev.ju.nextcart.controller;

import dev.ju.nextcart.exceptions.BadRequestException;
import dev.ju.nextcart.model.Product;
import dev.ju.nextcart.request.AddProductRequest;
import dev.ju.nextcart.request.UpdateProductRequest;
import dev.ju.nextcart.response.ApiResponse;
import dev.ju.nextcart.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    public static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    public static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllProducts() {
        log.info("Received request: getAllProducts");
        List<Product> products = productService.getAllProducts();
        log.debug("Products fetched: {}", products.size());
        return ResponseEntity.ok(new ApiResponse("Products found!", products));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("Product found!", product));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        System.out.println("Controller hit!");
        Product createdProduct = productService.addProduct(product);
        log.info("Controller entered!");
        return ResponseEntity.ok(new ApiResponse("Product created successfully!", createdProduct));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long productId) {
        try {
            Product product = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully!", product));
        } catch (BadRequestException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully!", productId));
        } catch (BadRequestException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/getByBrandAndName")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductByBrandAndName(brandName, productName);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products not found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Products found!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/getByBrand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductByBrand(brand);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products not found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Products found!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/getByProductName")
    public ResponseEntity<ApiResponse> getProductsByName(@RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByName(productName);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products not found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Products found!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/getByCategoryName")
    public ResponseEntity<ApiResponse> getProductsByCategoryName(@RequestParam String categoryName) {
        try {
            List<Product> products = productService.getProductByCategoryName(categoryName);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products not found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Products found!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("getByCategoryAndBrand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products not found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Products found!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/countByBrandProductName")
    public ResponseEntity<ApiResponse> countByBrandAndName(@RequestParam String brand, @RequestParam String productName) {
        try {
            Long count = productService.countProductsByBrandAndName(brand, productName);
            return ResponseEntity.ok(new ApiResponse("Products found!", count));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}

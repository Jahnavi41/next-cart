package dev.ju.nextcart.controller;

import dev.ju.nextcart.exceptions.BadRequestException;
import dev.ju.nextcart.model.Product;
import dev.ju.nextcart.response.ApiResponse;
import dev.ju.nextcart.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    public static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("Products found!", products));
        } catch (BadRequestException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
        }
    }
}

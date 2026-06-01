package com.example.Module_13;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class Controller {
   private static final Logger logger =
           LoggerFactory.getLogger(Controller.class);
    private final ProductService productService;
    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@Valid @RequestBody ProductEntity product){
        logger.info("REST request save to product with SKU");
        ProductEntity save=productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProduct(){
        logger.info("REST request to fetch all product");
        List<ProductEntity> productEntities=productService.getAllProduct();
        return ResponseEntity.ok(productEntities);
    }
    @GetMapping("/{Id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long Id){
        logger.info("REST request to view product by id");
        return productService.getProductById(Id).map(ResponseEntity::ok).orElseGet(()->{
            logger.warn("Product not found in this id");
            return ResponseEntity.notFound().build();
        });
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> update(@PathVariable Long id,@Valid @RequestBody ProductEntity product){
        try {
            logger.info("REST request to update Product with ID");
            ProductEntity productEntity=productService.update(id, product);
            return ResponseEntity.ok(productEntity);
        } catch (Exception e){
            logger.warn("Product update fail");
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        try {
            logger.info("REST request to delete product");
            productService.deleteById(id);
           return ResponseEntity.ok().build();
        } catch (Exception e) {
           return ResponseEntity.notFound().build();
        }
    }
}

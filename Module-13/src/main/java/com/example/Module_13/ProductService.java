package com.example.Module_13;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<ProductEntity> getAllProduct(){
        return productRepository.findAll();
    }
    public Optional<ProductEntity> getProductById(Long id){
        return productRepository.findById(id);
    }
    public ProductEntity createProduct(ProductEntity productEntity){
        return productRepository.save(productEntity);
    }
    public ProductEntity update(Long id,ProductEntity product){
        return productRepository.findById(id).map(productEntity -> {
            productEntity.setName(product.getName());
            productEntity.setDescription(product.getDescription());
            productEntity.setSku(product.getSku());
            productEntity.setPrice(product.getPrice());
            productEntity.setQuantity(product.getQuantity());
            productEntity.setStatus(product.getStatus());
            return productRepository.save(productEntity);
        }).orElseThrow(()->new RuntimeException("Product not found in this id"));
    }
    public void deleteById(Long id){
        if(!productRepository.existsById(id)){
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
}

package com.example.Module_13;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Must not be blank")
    @Column(nullable = false)
    private String name;
    @Size(max = 500,message = "must be no longer than 500 characters")
    private String description;
    @NotBlank(message = "SKU Must not be blank")
    @Column(nullable = false,unique = true)
    private String sku;
    @NotNull
    @Positive(message = "Must be greater than zero")
    @Column(nullable = false)
    private double price;
    @NotNull
    @Min(value = 0,message = "Quantity must be zero or more")
    @Column(nullable = false)
    private Integer quantity;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;
}

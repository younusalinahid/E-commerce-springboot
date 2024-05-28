package org.nahid.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;
    private String size;
    private String description;

    public Product(Long id, String name, int price, String size, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.description = description;
    }

    public Product(long id, String name, int price, String size, String description, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.description = description;
        this.category = category;
    }

    //    Product image

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}

package org.nahid.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    private List<Company> companies = new ArrayList<>();

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

//    @OneToOne
//    @JoinColumn(name = "discount_id")
//    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

}

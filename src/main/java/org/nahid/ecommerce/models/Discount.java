package org.nahid.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Discount {

    @Id
    @GeneratedValue
    private Long id;
    private String percentage;

    @OneToMany(mappedBy = "discount")
    @JsonManagedReference
    private List<Product> products;

    public Discount(Long id, String percentage) {
        this.id = id;
        this.percentage = percentage;
    }
}
